package wro.br.ufpe.cin.selecaoiot;

import java.io.Serializable;

/**
 * Created by Walber on 11/08/2017.
 */
//Objeto base
public class Serie implements Serializable {
    /**Foram escolhidos apenas alguns itens principais para serem mostrados na tela,
     *  com intuito de diminuir a poluicao visual**/
    private String nome;
    private String urlImagem;
    private String urlTvMaze;
    private String idioma;
    private double media;
    private String siteOficial;
    private String resumo;

    public Serie(String nome, String urlImagem, String urlTvMaze, String idioma,  double media, String siteOficial, String resumo){
        this.nome = nome;
        this.urlImagem = urlImagem;
        this.urlTvMaze = urlTvMaze;
        this.idioma = idioma;
        this.media = media;
        this.siteOficial = siteOficial;
        this.resumo = resumo;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public void setUrlTvMaze(String urlTvMaze) {
        this.urlTvMaze = urlTvMaze;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public void setMedia(Double media) {
        this.media = media;
    }

    public void setSiteOficial(String siteOficial) {
        this.siteOficial = siteOficial;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getNome() {
        return nome;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public String getUrlTvMaze() {
        return urlTvMaze;
    }

    public String getIdioma() {
        return idioma;
    }

    public double getMedia() {
        return media;
    }

    public String getSiteOficial() {
        return siteOficial;
    }
}
