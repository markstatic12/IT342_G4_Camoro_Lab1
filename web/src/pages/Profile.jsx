import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import '../styles/Profile.css';

const Profile = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleBackToDashboard = () => {
    navigate('/dashboard');
  };

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <div className="profile-container">
      <header className="profile-header">
        <div className="header-content">
          <button onClick={handleBackToDashboard} className="btn-back">
            ← Back to Dashboard
          </button>
          <button onClick={handleLogout} className="btn-danger">
            Logout
          </button>
        </div>
      </header>

      <main className="profile-main">
        <div className="profile-card">
          <div className="profile-avatar">
            <div className="avatar-circle">
              {user?.firstName?.[0]}{user?.lastName?.[0]}
            </div>
          </div>

          <div className="profile-info">
            <h1>{user?.firstName} {user?.lastName}</h1>
            <p className="profile-email">{user?.email}</p>
            <span className="profile-status">Active Account</span>
          </div>

          <div className="profile-details">
            <h2>Profile Information</h2>
            
            <div className="detail-group">
              <label>First Name</label>
              <div className="detail-value">{user?.firstName || 'N/A'}</div>
            </div>

            <div className="detail-group">
              <label>Last Name</label>
              <div className="detail-value">{user?.lastName || 'N/A'}</div>
            </div>

            <div className="detail-group">
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

          <div className="profile-actions">
            <button className="btn-primary">Edit Profile</button>
            <button className="btn-secondary">Change Password</button>
          </div>
        </div>
      </main>
    </div>
  );
};

export default Profile;
