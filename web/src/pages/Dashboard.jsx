import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import homeIcon from '../assets/miniapp_home.png';
import profileIcon from '../assets/miniapp_profile.png';
import logoutIcon from '../assets/miniapp_logout.png';
import '../styles/Dashboard.css';

const Dashboard = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const [showLogoutConfirm, setShowLogoutConfirm] = useState(false);

  const handleLogout = () => {
    setShowLogoutConfirm(true);
  };

  const confirmLogout = async () => {
    setShowLogoutConfirm(false);
    await logout();
    navigate('/login');
  };

  const handleViewProfile = () => {
    navigate('/profile');
  };

  return (
    <div className="dashboard-shell">
      <aside className="dashboard-sidebar">
        <div className="sidebar-brand">
          <div className="brand-mark">MA</div>
          <div>
            <p className="brand-title">Mini - App</p>
            <p className="brand-subtitle">User Portal</p>
          </div>
        </div>

        <nav className="sidebar-nav">
          <button type="button" className="nav-item active">
            <img className="nav-icon-img" src={homeIcon} alt="" aria-hidden="true" />
            Dashboard
          </button>
          <button type="button" className="nav-item" onClick={handleViewProfile}>
            <img className="nav-icon-img" src={profileIcon} alt="" aria-hidden="true" />
            View Profile
          </button>
        </nav>

        <div className="sidebar-footer">
          <button type="button" className="nav-item danger" onClick={handleLogout}>
            <img className="nav-icon-img" src={logoutIcon} alt="" aria-hidden="true" />
            Logout
          </button>
        </div>
      </aside>

      <main className="dashboard-content">
        <header className="content-header">
          <div>
            <p className="content-eyebrow">Overview</p>
            <h1>Dashboard</h1>
            <p className="content-subtitle">Welcome back, {user?.firstName || 'User'}.</p>
          </div>
        </header>

      </main>

      {showLogoutConfirm && (
        <div className="modal-overlay" role="dialog" aria-modal="true">
          <div className="modal-card">
            <h2>Confirm logout</h2>
            <p>Are you sure you want to log out?</p>
            <div className="modal-actions">
              <button type="button" className="ghost-button" onClick={() => setShowLogoutConfirm(false)}>
                Cancel
              </button>
              <button type="button" className="primary-button" onClick={confirmLogout}>
                Log out
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Dashboard;
