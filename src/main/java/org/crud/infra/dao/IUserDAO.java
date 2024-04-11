package org.crud.infra.dao;


import org.crud.models.User;

import java.util.Collection;

public interface IUserDAO {

    public Boolean cadastrar(User u);
    public User consultar(Long cpf);
    public void alterar(User c);
    public void excluir(Long cpf);
    public Collection<User> buscarTodos();
}

