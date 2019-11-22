package br.com.can.entity;

import java.io.Serializable;
import java.sql.Date;

import io.realm.RealmObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Usuario extends RealmObject implements Serializable {

    private long id;

    private String nome;

    //private Date dataNascimento;

    private long cpf;

    private String endereco;

    private String email;
   
    private String tipoUsuario;

    private String usuario;
   
    private String senha;
    
}
