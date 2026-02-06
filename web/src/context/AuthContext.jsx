/* eslint-disable react-refresh/only-export-components */
import { createContext, useContext, useState } from 'react';
import { authService } from '../services/authService';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(() => {
    const storedUser = localStorage.getItem('user');
    return storedUser ? JSON.parse(storedUser) : null;
  });
  const [loading] = useState(false);
  const [token, setToken] = useState(() => localStorage.getItem('token'));

  const login = async (email, password) => {
    try {
      const response = await authService.login(email, password);
      const { token, user, message } = response || {};

      if (!token || !user) {
        return {
          success: false,
          error: message || 'Email or password is incorrect.'
        };
      }

      localStorage.setItem('token', token);
      localStorage.setItem('user', JSON.stringify(user));

      setToken(token);
      setUser(user);

      return { success: true };
    } catch (error) {
      return { success: false, error: error.message || 'Email or password is incorrect.' };
    }
  };

  const register = async (userData) => {
    try {
      const response = await authService.register(userData);
      return { success: true, message: response.message };
    } catch (error) {
      return { success: false, error: error.message };
    }
  };

  const logout = async () => {
    try {
      // Wait for backend logout so the token is sent before we clear storage
      await authService.logout();
    } catch (error) {
      // Keep client logout even if backend call fails to avoid stuck sessions
      console.error('Logout failed:', error);
    } finally {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      setToken(null);
      setUser(null);
    }
  };

  const value = {
    user,
    token,
    login,
    register,
    logout,
    isAuthenticated: !!token,
    loading
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
