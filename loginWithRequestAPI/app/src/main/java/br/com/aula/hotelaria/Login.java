package br.com.aula.hotelaria;

public class Login {
    private String Usuario; //letras sensitives
    private String Senha;

    public Login(){} //método construtor

    public boolean Validarusuario(){
        if (this.Usuario.equals("")){
            return false;
        }
        else if (Senha.equals("")) {
            return false;
        }
        else if (!Usuario.equals("admin") || !Senha.equals("admin")) { //valida usuário
            return false;
        }
        else {
            return true;
        }
    }


    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }
}
