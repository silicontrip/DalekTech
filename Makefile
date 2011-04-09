
%.class:%.java
	javac $<

all: Cli.class Dalek.class DalekFactory.class DalekSection.class \
	DalekSectionDome.class DalekSectionSkirt.class DalekTech.class \
	Hex.class Map.class Player.class Position.class Tables.class \
	UserInterface.class Weapon.class Network.class NetworkClient.class \
	Guitwo.class selectFactoryDaleksPanel.class mapPanel.class \
	statusPanel.class Direction.class ProbabilityUI.class \
	BlackRenegadeDamage.class GreyRenegadeDamage.class  InvasionDamage.class  SpecialWeaponDamage.class \
	GoldSupremeDamage.class  ImperialDamage.class  RedSaucerDamage.class \
	MapImage.class AddComposite.class TacticalUI.class \
	GoldSupremeTactical.class ImperialTactical.class InvasionTactical.class \
	RedSaucerTactical.class RenegadeTactical.class SpecialWeaponTactical.class \
	WeaponUI.class



RenegadeTactical.class: TacticalUI.java RenegadeTactical.java
GoldSupremeTactical.class: TacticalUI.java GoldSupremeTactical.java
ImperialTactical.class: TacticalUI.java ImperialTactical.java
InvasionTactical.class: TacticalUI.java InvasionTactical.java
RedSaucerTactical.class: TacticalUI.java RedSaucerTactical.java
SpecialWeaponTactical.class: TacticalUI.java SpecialWeaponTactical.java

BlackRenegadeDamage.class: BlackRenegadeDamage.java DamageUI.java
GreyRenegadeDamage.class:  DamageUI.java
InvasionDamage.class: DamageUI.java
SpecialWeaponDamage.class: DamageUI.java
GoldSupremeDamage.class:  DamageUI.java
ImperialDamage.class:  DamageUI.java
RedSaucerDamage.class: DamageUI.java

