// web/src/pages/Dashboard.jsx
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import Sidebar from '../components/Sidebar';
import '../styles/Dashboard.css';

const Dashboard = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const [showLogoutConfirm, setShowLogoutConfirm] = useState(false);

  const handleNavigateDashboard = () => navigate('/dashboard');
  const handleNavigateProfile = () => navigate('/profile');
  const handleLogout = () => setShowLogoutConfirm(true);

  const confirmLogout = async () => {
    setShowLogoutConfirm(false);
    await logout();
    navigate('/login');
  };

  return (
    <div className="dashboard-shell">
      <Sidebar
        active="dashboard"
        onNavigateDashboard={handleNavigateDashboard}
        onNavigateProfile={handleNavigateProfile}
        onLogout={handleLogout}
      />

      <main className="dashboard-content">
        <header className="content-header">
          <div>
            <p className="content-eyebrow">Overview</p>
            <h1>Dashboard</h1>
            <p className="content-subtitle">Welcome back, {user?.firstName || 'User'}.</p>
          </div>
        </header>

        <section className="content-grid">
          <article className="surface-card">
            <h3>Account Status</h3>
            <div className="info-item">
              <span className="info-label">Email</span>
              <span className="info-value">{user?.email || 'N/A'}</span>
            </div>
            <div className="info-item">
              <span className="info-label">First Name</span>
              <span className="info-value">{user?.firstName || 'N/A'}</span>
            </div>
            <div className="info-item">
              <span className="info-label">Last Name</span>
              <span className="info-value">{user?.lastName || 'N/A'}</span>
            </div>
          </article>
        </section>
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