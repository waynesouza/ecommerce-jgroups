package com.ifmg.formiga.ecommerce.view;

import com.ifmg.formiga.ecommerce.enums.EnumChannel;
import com.ifmg.formiga.ecommerce.enums.EnumService;
import com.ifmg.formiga.ecommerce.model.Comunication;
import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.blocks.MessageDispatcher;
import org.jgroups.blocks.RequestHandler;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.ResponseMode;
import org.jgroups.util.Rsp;
import org.jgroups.util.RspList;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class View {

    private JChannel channel;
    private static MessageDispatcher dispatcher;
    private static Vector<Address> address;

    private void createGroup() {
        RequestOptions options = new RequestOptions();
        options.setMode(ResponseMode.GET_ALL);
        options.setAnycasting(false);
        Comunication comunication = new Comunication(EnumChannel.VIEW_CONTROL, EnumService.NEW_VIEW_MEMBER, null);
        Address cluster = null;
        Message newMessage = new Message(cluster, comunication);
        RspList<Comunication> list = null;
        try {
            list = dispatcher.castMessage(null, newMessage, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Rsp<Comunication> x : list) {
            if (x.getValue() != null && x.getValue().channel == EnumChannel.CONTROL_VIEW)
                address.add(x.getSender());
        }
    }

    private void createAccount() {

    }

    private void login() {
        Scanner op = new Scanner(System.in);
        while (!op.toString().equals("3")) {
            System.out.println("----- ACESSAR CONTA -----\n");
            System.out.println("1) Consumidor");
            System.out.println("2) Vendedor");
            System.out.println("3) Voltar\n");
            System.out.print("Informe a opção desejada: ");
            op.nextLine();
            switch (op.toString()) {
                case "1":
                    loginConsumer();
                    break;
                case "2":
                    loginSeller();
                    break;
                case "3":
                    // TODO mainMenu()
                    break;
                default:
                    System.out.println("Opção inválida\n");
                    break;
            }
        }
    }

    private void loginConsumer() {
        Scanner op = new Scanner(System.in);
        while (!op.toString().equals("3")) {
            System.out.println("----- ACESSAR CONTA - CONSUMIDOR -----\n");
            System.out.print("Informe o CPF: ");
            String cpf = op.nextLine();
            System.out.print("Informe a senha: ");
            String password = op.nextLine();

            ArrayList<Object> content = new ArrayList<>();
            content.add(cpf);
            content.add(password);

            Comunication comunication = new Comunication(EnumChannel.VIEW_CONTROL, EnumService.LOGIN_CONSUMER, content);
            comunication = sendMessage(comunication);
            comunication.content.stream().findFirst();
            // TODO comunication.content.get(0)
        }
    }

    private void loginSeller() {
        Scanner op = new Scanner(System.in);
        while (!op.toString().equals("3")) {
            System.out.println("----- ACESSAR CONTA - VENDEDOR -----\n");
            System.out.print("Informe o CPF: ");
            String cpf = op.nextLine();
            System.out.print("Informe a senha: ");
            String password = op.nextLine();

            ArrayList<Object> content = new ArrayList<>();
            content.add(cpf);
            content.add(password);

            Comunication comunication = new Comunication(EnumChannel.VIEW_CONTROL, EnumService.LOGIN_CONSUMER, content);
            // TODO sendMessage(comunication)
            // TODO comunication.content.get(0)
        }
    }

    private Comunication sendMessage(Comunication comunication) {
        Vector<Address> cluster = address;
        RequestOptions options = new RequestOptions();
        options.setMode(ResponseMode.GET_FIRST);
        options.setAnycasting(true);
        Message message = new Message(null, comunication);
        RspList<Comunication> responseComunication = null;
        try {
            responseComunication = dispatcher.castMessage(cluster, message, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseComunication.getFirst();
    }

}
