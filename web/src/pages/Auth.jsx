import { useEffect, useMemo, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import AuthHighlights from '../components/AuthHighlights';
import '../styles/Auth.css';

const LoginForm = ({ data, onChange, onSubmit, loading, error }) => (
	<>
		<div className="panel-header">
			<p className="eyebrow">Welcome back</p>
			<h1>Access your workspace</h1>
			<p className="muted">Enter your credentials to continue to the dashboard.</p>
		</div>
		<form onSubmit={onSubmit} className="auth-form">
			{error && <div className="alert error">{error}</div>}
			<div className="field">
				<label htmlFor="login-email">Email</label>
				<input
					id="login-email"
					name="email"
					type="email"
					value={data.email}
					onChange={onChange}
					placeholder="name@company.com"
					disabled={loading}
					autoComplete="email"
				/>
			</div>
			<div className="field">
				<label htmlFor="login-password">Password</label>
				<input
					id="login-password"
					name="password"
					type="password"
					value={data.password}
					onChange={onChange}
					placeholder="Enter your password"
					disabled={loading}
					autoComplete="current-password"
				/>
			</div>
			<div className="actions">
				<button type="submit" className="btn-primary" disabled={loading}>
					{loading ? 'Signing in...' : 'Sign in'}
				</button>
			</div>
		</form>
	</>
);

const RegisterForm = ({ data, onChange, onSubmit, loading, error }) => (
	<>
		<div className="panel-header">
			<p className="eyebrow">Create account</p>
			<h1>Join the workspace</h1>
			<p className="muted">Set up your profile to unlock your dashboard.</p>
		</div>
		<form onSubmit={onSubmit} className="auth-form">
			{error && <div className="alert error">{error}</div>}
			<div className="field-row">
				<div className="field">
					<label htmlFor="firstName">First name</label>
					<input
						id="firstName"
						name="firstName"
						type="text"
						value={data.firstName}
						onChange={onChange}
						placeholder="Alex"
						disabled={loading}
						autoComplete="given-name"
					/>
				</div>
				<div className="field">
					<label htmlFor="lastName">Last name</label>
					<input
						id="lastName"
						name="lastName"
						type="text"
						value={data.lastName}
						onChange={onChange}
						placeholder="Smith"
						disabled={loading}
						autoComplete="family-name"
					/>
				</div>
			</div>
			<div className="field">
				<label htmlFor="register-email">Email</label>
				<input
					id="register-email"
					name="email"
					type="email"
					value={data.email}
					onChange={onChange}
					placeholder="name@company.com"
					disabled={loading}
					autoComplete="email"
				/>
			</div>
			<div className="field-row">
				<div className="field">
					<label htmlFor="password">Password</label>
					<input
						id="password"
						name="password"
						type="password"
						value={data.password}
						onChange={onChange}
						placeholder="Create a password"
						disabled={loading}
						autoComplete="new-password"
					/>
				</div>
				<div className="field">
					<label htmlFor="confirmPassword">Confirm password</label>
					<input
						id="confirmPassword"
						name="confirmPassword"
						type="password"
						value={data.confirmPassword}
						onChange={onChange}
						placeholder="Re-enter password"
						disabled={loading}
						autoComplete="new-password"
					/>
				</div>
			</div>
			<div className="actions">
				<button type="submit" className="btn-primary" disabled={loading}>
					{loading ? 'Creating account...' : 'Create account'}
				</button>
			</div>
		</form>
	</>
);

const Auth = ({ initialMode = 'login' }) => {
	const { login, register } = useAuth();
	const navigate = useNavigate();
	const location = useLocation();

	const [mode, setMode] = useState(initialMode);
	const [loginData, setLoginData] = useState({ email: '', password: '' });
	const [registerData, setRegisterData] = useState({
		firstName: '',
		lastName: '',
		email: '',
		password: '',
		confirmPassword: '',
	});
	const [error, setError] = useState('');
	const [loading, setLoading] = useState(false);

	useEffect(() => {
		if (location.pathname.includes('register')) {
			setMode('register');
		} else {
			setMode('login');
		}
		setError('');
	}, [location.pathname]);

	const handleModeChange = (target) => {
		setMode(target);
		setError('');
		navigate(target === 'login' ? '/login' : '/register');
	};

	const onLoginChange = (e) => {
		setLoginData({ ...loginData, [e.target.name]: e.target.value });
		setError('');
	};

	const onRegisterChange = (e) => {
		setRegisterData({ ...registerData, [e.target.name]: e.target.value });
		setError('');
	};

	const validateLogin = () => {
		if (!loginData.email || !loginData.password) {
			setError('Please fill in all fields');
			return false;
		}
		if (!/\S+@\S+\.\S+/.test(loginData.email)) {
			setError('Please enter a valid email address');
			return false;
		}
		return true;
	};

	const validateRegister = () => {
		const { firstName, lastName, email, password, confirmPassword } = registerData;
		if (!firstName || !lastName || !email || !password || !confirmPassword) {
			setError('Please fill in all fields');
			return false;
		}
		if (!/\S+@\S+\.\S+/.test(email)) {
			setError('Please enter a valid email address');
			return false;
		}
		if (password.length < 6) {
			setError('Password must be at least 6 characters long');
			return false;
		}
		if (password !== confirmPassword) {
			setError('Passwords do not match');
			return false;
		}
		return true;
	};

	const handleLoginSubmit = async (e) => {
		e.preventDefault();
		if (!validateLogin()) return;
		setLoading(true);
		setError('');
		const result = await login(loginData.email, loginData.password);
		if (result.success) {
			navigate('/dashboard');
		} else {
			setError(result.error || 'Invalid email or password');
		}
		setLoading(false);
	};

	const handleRegisterSubmit = async (e) => {
		e.preventDefault();
		if (!validateRegister()) return;
		setLoading(true);
		setError('');
		const result = await register({
			firstName: registerData.firstName,
			lastName: registerData.lastName,
			email: registerData.email,
			password: registerData.password,
		});
		if (result.success) {
			navigate('/login');
			setMode('login');
		} else {
			setError(result.error || 'Registration failed. Please try again.');
		}
		setLoading(false);
	};

	const isLogin = mode === 'login';

	const formPanels = useMemo(
		() => ({
			login: (
				<div className={`form-panel ${isLogin ? 'active' : 'exit-left'}`}>
					<LoginForm
						data={loginData}
						onChange={onLoginChange}
						onSubmit={handleLoginSubmit}
						loading={loading}
						error={isLogin ? error : ''}
					/>
				</div>
			),
			register: (
				<div className={`form-panel ${!isLogin ? 'active' : 'exit-right'}`}>
					<RegisterForm
						data={registerData}
						onChange={onRegisterChange}
						onSubmit={handleRegisterSubmit}
						loading={loading}
						error={!isLogin ? error : ''}
					/>
				</div>
			),
		}),
		[isLogin, loginData, registerData, loading, error]
	);

	return (
		<div className="auth-shell">
			<div className="auth-surface">
				<section className="auth-left" aria-live="polite">
					<div className="toggle-row" role="tablist" aria-label="Authentication mode">
						<button
							type="button"
							role="tab"
							aria-selected={isLogin}
							className={`toggle-button ${isLogin ? 'active' : ''}`}
							onClick={() => handleModeChange('login')}
						>
							Login
						</button>
						<button
							type="button"
							role="tab"
							aria-selected={!isLogin}
							className={`toggle-button ${!isLogin ? 'active' : ''}`}
							onClick={() => handleModeChange('register')}
						>
							Register
						</button>
					</div>

					<div className="form-stack" aria-live="polite">
						{formPanels.login}
						{formPanels.register}
					</div>

		
				</section>

				<section className="auth-right" aria-label="Highlights">
					<AuthHighlights />
				</section>
			</div>
		</div>
	);
};

export default Auth;
