package graph;

public enum Relacao {
	ATORE("ato-re"), 	AHIRO("ahi-ro"), 		KOKORE("koko-re"), 	HAHARE("haha-re"), 	MAMALO("mama-lo"), 
	KEKERO("keke-ro"), 	NOREHOI("noheroĩ"), 	YAYARE("yaya-re"), 	YOWARE("yowa-re"), 	YAYALO("yaya-lo"), 
	YOWALO("yowa-lo"), 	NODAISE("nodai-se"),	TAWIHI("tawihi"), 	ETAI("etaĩ"), 		NODAIXO("nodaixo"), 
	TAWIRO("tawiro"), 	NOXIWETE("noxiwete"), 	NOXIWETO("noxiwete"),
	
	NERANI("nerani"), 				NERANETO("neranetõ"), 			NOWATORE("nowatore"), 			NONATONAWENE("nonatonawene"),
	NOWATOLO("nowatolo"),			NIASERO("niasero"), 			NOTENE("notene"), 				NOXINETO("noxineto"), 
	NETAIAKERO("netai-akero"), 		TAWIHIEKOKWE("tawihi-ekokwe"), 	TAWIROEKOKWE("tawiro-ekokwe"), 	NONATONAWENERO("nonatonawene-ro"),
	NODAESEAKERO("nodaese-akero"), 	NODAEXOAKERO("nodaexo-akero"), 	NIATOKWE("niatokwe"), 			NETAIEKOKWE("netai-ekokwe");
	
	private String nome;
	
	Relacao(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return this.nome;
	}
}
