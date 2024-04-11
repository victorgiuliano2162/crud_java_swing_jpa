package src.org.crud.swing;

import src.org.crud.infra.dao.IUserDAO;
import src.org.crud.infra.dao.UserDAO;
import src.org.crud.models.User;

import javax.swing.*;
import java.awt.*;

public class Painel extends JPanel{

    static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 600;
    IUserDAO userDAO = new UserDAO();

    public Painel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.white);
        this.setFocusable(true);

        startCrud(1);
    }

    public User tratarDados(String dados) {
        if(dados == null) startCrud(0);
        String[] dadosSeparados = dados.split(",");
        if(dadosSeparados.length < 7 ) startCrud(2);

        User user = new User(dadosSeparados[0],dadosSeparados[1],dadosSeparados[2],dadosSeparados[3],dadosSeparados[4],dadosSeparados[5],dadosSeparados[6]);
        userDAO.cadastrar(user);
        return user;
    }

    public void startCrud(int a) {
        String opcao = "";
        if (a == 1) {
        opcao = JOptionPane.showInputDialog(null,"Digite: \n1 para cadastro \n2 para consulta \n3 para exclusão \n4 para alteração \n5 para sair","Cadastro", JOptionPane.INFORMATION_MESSAGE);
        } else if(a == 0) {
            opcao = JOptionPane.showInputDialog(null,"Preencha os campos para efetuar o cadastro\nDigite: \n1 para cadastro \n2 para consulta \n3 para exclusão \n4 para alteração \n5 para sair","Cadastro", JOptionPane.INFORMATION_MESSAGE);
        } else if(a == 2) {
            opcao = JOptionPane.showInputDialog(null,"Os campos em branco devem constar como um espaço em branco entre vírgulas.\nDigite na ordem:\nnome, cpf, telefone, endereço, número, cidade, estado\nDigite: \n1 para cadastro \n2 para consulta \n3 para exclusão \n4 para alteração \n5 para sair","Cadastro", JOptionPane.INFORMATION_MESSAGE);
        }

        if(opcao.equals("1")){
            String dados = JOptionPane.showInputDialog(null, "Digite os dados do cliente separados por vírgula, na seguinte ordem: nome, cpf, telefone, endereço, número, cidade, estado", "cadastro", JOptionPane.INFORMATION_MESSAGE);
            tratarDados(dados);
        } else if(opcao.equals("2")){

        } else if(opcao.equals("3")){

        } else if(opcao.equals("4")){

        } else if(opcao.equals("5")){
            System.exit(0);
        }
    }
}
