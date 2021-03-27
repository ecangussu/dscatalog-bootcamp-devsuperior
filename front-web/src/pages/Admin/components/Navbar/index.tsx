import React from 'react';
import { NavLink } from 'react-router-dom';
import './styles.scss';

const Navbar = () => (
  <nav className="admin-nav-container">
    <ul>
      <li>
        <NavLink to="/admin/products" className="admin-nav-item">
          MEUS PRODUTOS
        </NavLink>
      </li>
      <li>
        <NavLink to="/admin/categories" className="admin-nav-item">
          MINHAS CATEGORIAS
        </NavLink>
      </li>
      <li>
        <NavLink to="/admin/users" className="admin-nav-item">
          MEUS USUÁRIOS
        </NavLink>
      </li>
    </ul>
  </nav>
);

export default Navbar;