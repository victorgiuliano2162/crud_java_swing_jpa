package org.crud.infra.dao;


import org.crud.models.User;

import java.util.Collection;
import java.util.List;

public interface IUserDAO<E> {

    public Boolean cadastrar(User u);
    public User consultar(String cpf);
    public void alterar(User c);
    public void excluir(String cpf);
    public List<E> buscarTodos();
}

