import React from 'react';

const base = {
  width: 20, height: 20, viewBox: '0 0 24 24', fill: 'none', stroke: 'currentColor',
  strokeWidth: 1.8, strokeLinecap: 'round', strokeLinejoin: 'round', 'aria-hidden': true,
};
const icon = (children) => (props) => <svg {...base} {...props}>{children}</svg>;

export const ChefHat = icon(<><path d="M7 14.5A4 4 0 0 1 6 7a5 5 0 0 1 9.6-1.4A4.5 4.5 0 0 1 17 14.5V19H7z" /><path d="M7 17h10" /></>);
export const SearchIcon = icon(<><circle cx="11" cy="11" r="6.5" /><path d="m20 20-4-4" /></>);
export const Clock = icon(<><circle cx="12" cy="12" r="8.5" /><path d="M12 7.5V12l3 2" /></>);
export const Users = icon(<><circle cx="9" cy="8" r="3.2" /><path d="M3 19c.6-3 3-4.5 6-4.5s5.4 1.5 6 4.5" /><path d="M16 5.2a3 3 0 0 1 0 5.6M18 14.8c1.7.6 2.7 2 3 4.2" /></>);
export const Gauge = icon(<><path d="M4 16a8 8 0 1 1 16 0" /><path d="m12 16 3.5-4.5" /></>);
export const Back = icon(<path d="M15 5l-7 7 7 7" />);
export const Close = icon(<path d="M6 6l12 12M18 6 6 18" />);
export const Plus = icon(<path d="M12 5v14M5 12h14" />);
export const Menu = icon(<path d="M4 7h16M4 12h16M4 17h16" />);
export const Heart = ({ filled, ...p }) => (
  <svg {...base} {...p} fill={filled ? 'currentColor' : 'none'}>
    <path d="M12 20s-7.5-4.6-7.5-10.2A4.3 4.3 0 0 1 12 7.3a4.3 4.3 0 0 1 7.5 2.5C19.5 15.4 12 20 12 20z" />
  </svg>
);