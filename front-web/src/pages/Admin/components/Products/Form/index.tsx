import { makeRequest } from 'core/utils/request';
import React, { useState } from'react';
import BaseForm from '../../BaseForm';
import './styles.scss';

type FormState = {
  name: string;
  price: string;
  category: string;
  description: string;
}

type FormEvent = React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>;

const Form = () => {
  const [formData, setFormData] = useState<FormState>({
    name: '',
    price: '',
    category: '1',
    description: ''
  });

  const handleOnChange = (event: FormEvent) => {
    const name = event.target.name;
    const value = event.target.value;
    setFormData(data => ({ ...data, [name]: value}));
  }

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const payload = {
      ...formData,
      imgUrl: 'https://www.techinn.com/f/13777/137776929/microsoft-xbox-series-x-1tb.jpg',
      categories: [{id: formData.category}]
    }

    makeRequest({url: '/products', method: 'POST', data: payload})
      .then(() => {
        setFormData({name: '', price: '', category: '', description: ''});
      });
  }

  return (
    <form onSubmit={handleSubmit}>
      <BaseForm title="cadastrar um produto">
        <div className="row">
          <div className="col-6">
            <input
              value={formData.name}
              name="name"
              type="text" 
              className="form-control mb-5" 
              onChange={handleOnChange}
              placeholder="Nome do Produto"
            />
            <select
              value={formData.category}
              name="category"
              className="form-control mb-5" 
              onChange={handleOnChange}
            >
              <option value="3">Computadores</option>
              <option value="2">Eletrônicos</option>
              <option value="1">Livros</option>
            </select>
            <input
              value={formData.price}
              name="price"
              type="text" 
              className="form-control" 
              onChange={handleOnChange}
              placeholder="Preço"
            />
          </div>
          <div className="col-6">
            <textarea 
              name="description" 
              onChange={handleOnChange} 
              value={formData.description}
              cols={30} 
              rows={10} 
              className="form-control"
            />
          </div>
        </div>
      </BaseForm>
    </form>
  )
}

export default Form;