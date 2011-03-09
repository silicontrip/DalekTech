
public class DalekSectionDome extends DalekSection {

void auxilaryDamage () {
	this.dalek.setBase(this.dalek.getBase() + 2);
	super.auxilaryDamage();
}

	public DalekSectionDome (String name, int damage, DalekSection transfer) {
		super(name,damage,transfer);
	}
}