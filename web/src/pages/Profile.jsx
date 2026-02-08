import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import Sidebar from '../components/Sidebar';
import '../styles/Profile.css';

const Profile = () => {
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
    <div className="profile-shell">
      <Sidebar
        active="profile"
        onNavigateDashboard={handleNavigateDashboard}
        onNavigateProfile={handleNavigateProfile}
        onLogout={handleLogout}
      />

      <main className="profile-content">
        <header className="content-header">
          <div>
            <p className="content-crumb">Account / Profile</p>
            <h1>Profile</h1>
            <p className="content-subtitle">Manage your personal details and security.</p>
          </div>
          <div className="header-actions">
            <button onClick={handleNavigateDashboard} className="ghost-button">
              Back to Dashboard
            </button>
          </div>
        </header>

        <section className="profile-grid">
          <article className="profile-identity">
            <div className="profile-avatar">
              <div className="avatar-circle">
                {user?.firstName?.[0]}
                {user?.lastName?.[0]}
              </div>
            </div>
            <div className="profile-info">
              <h2>
                {user?.firstName} {user?.lastName}
              </h2>
              <p className="profile-email">{user?.email}</p>
              <span className="status-pill">Active Account</span>
            </div>
            <div className="profile-actions">
              <button className="primary-button">Edit Profile</button>
            </div>
          </article>

          <article className="profile-details-card">
            <div className="detail-grid info-grid">
              <div className="detail-group">
                <label>First Name</label>
                <div className="detail-value">{user?.firstName || 'N/A'}</div>
              </div>
              <div className="detail-group">
                <label>Last Name</label>
                <div className="detail-value">{user?.lastName || 'N/A'}</div>
              </div>
              <div className="detail-group full">
                <label>Email Address</label>
                <div className="detail-value">{user?.email || 'N/A'}</div>
              </div>
              <div className="detail-group">
                <label>Account Created</label>
                <div className="detail-value">
                  {user?.createdAt ? new Date(user.createdAt).toLocaleDateString() : 'N/A'}
                </div>
              </div>
              <div className="detail-group">
                <label>Last Updated</label>
                <div className="detail-value">
                  {user?.updatedAt ? new Date(user.updatedAt).toLocaleDateString() : 'N/A'}
                </div>
              </div>
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

export default Profile;