
import React, { createContext, useContext, useState, useEffect } from 'react';
import { authService } from '../services/api';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  // Check existing login when application starts
  useEffect(() => {
    const stored = localStorage.getItem('user');
    const token = localStorage.getItem('token');

    if (stored && token) {
      try {
        setUser(JSON.parse(stored));
      } catch (e) {
        localStorage.removeItem('user');
        localStorage.removeItem('token');
      }
    }

    setLoading(false);
  }, []);

  // LOGIN USING EMAIL
  const login = async (credentials) => {
    const response = await authService.login({
      email: credentials.email,
      password: credentials.password,
    });

    const { token, ...userData } = response.data;
    const finalUser = userData.user || userData;

    localStorage.setItem('token', token);
    localStorage.setItem('user', JSON.stringify(finalUser));

    setUser(finalUser);

    return finalUser;
  };

  // REGISTER NEW USER
  const register = async (data) => {
    const response = await authService.register(data);
    return response.data;
  };

  // LOGOUT
  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    setUser(null);
  };

  return (
    <AuthContext.Provider
      value={{
        user,
        login,
        logout,
        register,
        loading,
        isLoggedIn: !!user,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
