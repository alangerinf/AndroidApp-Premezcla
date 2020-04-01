package com.ibao.premescla;

public class ConectionConfig {
    private static final String ROOT= "http://35.167.15.182/Premezcla/Copacabana/Requests/";
    public static final String GET_ORDER=ROOT+"getOrden.php";
    public static final String GET_TANCADA=ROOT+"getTancada.php";
    public static final String GET_NEXTPPESADO=ROOT+"getNextProductoPesado.php";

    //todo: modificar nombre del php de autentificacion para el login
    public static final String POST_LOGIN = ROOT+"autentificacion.php";

    public static final String POST_TANCADA=ROOT+"postTancada.php",
            T_ACTION_DOSIFICACION="dosificacion",
            T_ACTION_MEZCLA="mezcla",
            T_ACTION_APLICACION="aplicacion";

    public static final String POST_MUESTRA=ROOT+"postMuestra.php";
    public static final String POST_PPESADO =ROOT+"postProductoPesado.php";

    public static final String GET_TRACTOR_CONDUCTOR=ROOT+"getTractorNConductor.php";


}
