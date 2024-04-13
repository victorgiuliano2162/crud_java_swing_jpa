package src.org.crud.infra.dao;


import src.org.crud.models.User;

import java.util.Collection;

public interface IUserDAO {

    public Boolean cadastrar(User u);
    public User consultar(String cpf);
    public void alterar(User c);
    public void excluir(String cpf);
    public Collection<User> buscarTodos();
}

