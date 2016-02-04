package production.modele;

public class Composition {
	
	private int code;
	private Nomenclature nomenclature_un;
	private Nomenclature nomenclature_deux;

	public Composition(int code, Nomenclature nomenclature_un, Nomenclature nomenclature_deux) {
		super();
		this.code = code;
		this.nomenclature_un = nomenclature_un;
		this.nomenclature_deux = nomenclature_deux;
	}
	
	public int getCode() {
		return code;
	}

	public Nomenclature getNomenclatureUn() {
		return nomenclature_un;
	}

	public Nomenclature getNomenclatureDeux() {
		return nomenclature_deux;
	}

	public void setCode(int code) {
		this.code = code;
	}
	public void setNomenclatureUn(Nomenclature nomenclature_un) {
		this.nomenclature_un = nomenclature_un;
	}

	public void setNomenclatureDeux(Nomenclature nomenclature_deux) {
		this.nomenclature_deux = nomenclature_deux;
	}	
}
