// web/src/components/Sidebar.jsx
import homeIcon from '../assets/miniapp_home.png';
import profileIcon from '../assets/miniapp_profile.png';
import '../styles/Sidebar.css';


export default function Sidebar({ active, onNavigateDashboard, onNavigateProfile, onLogout }) {
  return (
    <aside className="dashboard-sidebar">
      <div className="sidebar-brand">
        <div className="brand-mark">MA</div>
        <div>
          <p className="brand-title">Mini - App</p>
          <p className="brand-subtitle">User Portal</p>
        </div>
      </div>

      <nav className="sidebar-nav">
        <button
          type="button"
          className={`nav-item ${active === 'dashboard' ? 'active' : ''}`}
          onClick={onNavigateDashboard}
        >
          <img className="nav-icon-img" src={homeIcon} alt="" aria-hidden="true" />
          Dashboard
        </button>
        <button
          type="button"
          className={`nav-item ${active === 'profile' ? 'active' : ''}`}
          onClick={onNavigateProfile}
        >
          <img className="nav-icon-img" src={profileIcon} alt="" aria-hidden="true" />
          View Profile
        </button>
      </nav>

      <div className="sidebar-footer">
        <button type="button" className="nav-item danger" onClick={onLogout}>
          <span className="nav-icon">⎋</span>
          Logout
        </button>
      </div>
    </aside>
  );
}