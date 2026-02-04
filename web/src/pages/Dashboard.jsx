import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import '../styles/Dashboard.css';

const Dashboard = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  const handleViewProfile = () => {
    navigate('/profile');
  };

  return (
    <div className="dashboard-container">
      <header className="dashboard-header">
        <div className="header-content">
          <h1>Dashboard</h1>
          <div className="header-actions">
            <button onClick={handleViewProfile} className="btn-secondary">
              View Profile
            </button>
            <button onClick={handleLogout} className="btn-danger">
              Logout
            </button>
          </div>
        </div>
      </header>

      <main className="dashboard-main">
        <div className="welcome-section">
          <h2>Welcome back, {user?.firstName || 'User'}!</h2>
          <p>You have successfully logged in to your account.</p>
        </div>

        <div className="dashboard-grid">
          <div className="dashboard-card">
            <h3>Account Information</h3>
            <div className="info-item">
              <span className="info-label">Name:</span>
              <span className="info-value">
                {user?.firstName} {user?.lastName}
              </span>
            </div>
            <div className="info-item">
              <span className="info-label">Email:</span>
              <span className="info-value">{user?.email}</span>
            </div>
            <div className="info-item">
              <span className="info-label">Status:</span>
              <span className="info-value status-active">Active</span>
            </div>
          </div>

          <div className="dashboard-card">
            <h3>Quick Actions</h3>
            <button onClick={handleViewProfile} className="action-button">
              View Full Profile
            </button>
            <button className="action-button">
              Update Settings
            </button>
            <button className="action-button">
              Change Password
            </button>
          </div>

          <div className="dashboard-card">
            <h3>Recent Activity</h3>
            <p className="placeholder-text">No recent activity to display.</p>
          </div>
        </div>
      </main>
    </div>
  );
};

export default Dashboard;
