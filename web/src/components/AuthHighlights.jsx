const features = [
  {
    title: 'Secure authentication',
    description: 'Credentials stay encrypted in transit and at rest.',
  },
  {
    title: 'Fast access',
    description: 'Snappy sign-in with instant dashboard handoff.',
  },
  {
    title: 'Personal dashboard',
    description: 'Everything you need in one secure, focused view.',
  },
  {
    title: 'Human-centered UI',
    description: 'Clean layouts, high contrast, and accessible controls.',
  },
];

const AuthHighlights = () => {
  return (
    <aside className="auth-showcase" aria-label="Product highlights">
      <div className="brand-block">
        <div className="brand-mark">MA</div>
        <div>
          <p className="eyebrow">Mini App Suite</p>
          <h2>Modern access experience</h2>
          <p className="muted">Designed for clarity, confidence, and speed.</p>
        </div>
      </div>

      <ul className="feature-list">
        {features.map((feature) => (
          <li key={feature.title} className="feature-item">
            <span className="feature-icon" aria-hidden="true" />
            <div>
              <p className="feature-title">{feature.title}</p>
              <p className="muted">{feature.description}</p>
            </div>
          </li>
        ))}
      </ul>
    </aside>
  );
};

export default AuthHighlights;
