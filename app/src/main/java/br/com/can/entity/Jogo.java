package br.com.can.entity;

import java.io.Serializable;

import io.realm.RealmObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Jogo extends RealmObject implements Serializable {

    private String categoria;
    private String competicao;
    private String nomeClubeA;
    private String nomeClubeB;
    private String hora;
    private String local;
    private String numeroGolA;
    private String numeroGolB;

}
