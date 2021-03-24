import React from 'react';
import './styles.scss';

const Navbar = () => (
  <nav className="admin-nav-container">
    <ul>
      <li>
        <a href="link" className="admin-nav-item active">MEUS PRODUTOS</a>
      </li>
      <li>
        <a href="link" className="admin-nav-item">MINHAS CATEGORIAS</a>
      </li>
      <li>
        <a href="link" className="admin-nav-item">MEUS USUÁRIOS</a>
      </li>
    </ul>
  </nav>
);

export default Navbar;