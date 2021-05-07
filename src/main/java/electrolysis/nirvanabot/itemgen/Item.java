package electrolysis.nirvanabot.itemgen;

public class Item {
    private final double MIN_POS_ID = 0.3;
    private final double MAX_ID = 1.3;
    private final double MIN_NEG_ID = 0.7;

    public String getName() {
        return name;
    }

    private String name;
    private String attackSpeed;
    private String tier;
    private String set;

    private int sockets; //powder slots

    private String type; //what type of item it is lol
    private String accessoryType;

    private String armorType;
    private String armorColor;

    private String addedLore;

    private String dropType;
    private boolean identified;

    private int health;
    private int fireDefense;
    private int waterDefense;
    private int airDefense;
    private int thunderDefense;
    private int earthDefense;

    private String damage;
    private String fireDamage;
    private String waterDamage;
    private String airDamage;
    private String thunderDamage;
    private String earthDamage;

    private int level;
    private String quest;
    private String classRequirement;

    //skill point reqs
    private int strength;
    private int dexterity;
    private int intelligence;
    private int agility;
    private int defense;

    private int healthRegen;
    private int manaRegen;
    private int spellDamage;
    private int damageBonus; //melee damage
    private int lifeSteal;
    private int manaSteal;

    private int xpBonus;
    private int lootBonus;

    private int reflection;
    private int strengthPoints;
    private int defensePoints;
    private int dexterityPoints;
    private int intelligencePoints;
    private int agilityPoints;

    private int thorns;
    private int exploding;
    private int speed;

    private int attackSpeedBonus;
    private int poison;

    private int healthBonus;

    private int soulPoints;
    private int emeraldStealing;

    private int healthRegenRaw;
    private int spellDamageRaw;
    private int damageBonusRaw;

    private int bonusFireDamage;
    private int bonusWaterDamage;
    private int bonusAirDamage;
    private int bonusThunderDamage;
    private int bonusEarthDamage;

    private int bonusFireDefense;
    private int bonusWaterDefense;
    private int bonusAirDefense;
    private int bonusThunderDefense;
    private int bonusEarthDefense;

    private int knockback;

    private String category;

    public String generateItemThing() {

        if (type == null && accessoryType != null) {
            type = accessoryType;

        }

        String itemThing = "";

        itemThing += "```ml" +"\n"
                + name +" (" + tier + " " + type + ")" + "\n" //name, tier and type
                + "\n";

        //adds a health value to the thing if it ain't 0 lol
        if (health != 0) {
            itemThing += "Health: " + health
                    + "\n\n";

        } //display raw elemental defenses
        if (earthDefense != 0) {
            itemThing += "Earth Defense: " + earthDefense
                    + "\n";
        }
        if (thunderDefense != 0) {
            itemThing += "Thunder Defense: " + thunderDefense + "\n";
        }
        if (waterDefense != 0) {
            itemThing += "Water Defense: " + waterDefense + "\n";
        }
        if (fireDefense != 0) {
            itemThing += "Fire Defense: " + fireDefense + "\n";
        }
        if (airDefense != 0) {
            itemThing += "Air Defense: " + airDefense + "\n";
        }


        if (category.equalsIgnoreCase("weapon")) {
            if (attackSpeed != null) {
                itemThing += attackSpeed + " attack speed\n\n";
            }
            if (damage != null && !(damage.equalsIgnoreCase("0-0"))) {
                itemThing += "Neutral Damage: " + damage + "\n";
            }
            if (earthDamage != null && !(earthDamage.equalsIgnoreCase("0-0"))) {
                itemThing += "Earth Damage: " + earthDamage + "\n";
            }
            if (thunderDamage != null && !(thunderDamage.equalsIgnoreCase("0-0"))) {
                itemThing += "Thunder Damage: " + thunderDamage + "\n";
            }
            if (waterDamage != null && !(waterDamage.equalsIgnoreCase("0-0"))) {
                itemThing += "Water Damage: " + waterDamage + "\n";
            }
            if (fireDamage != null && !(fireDamage.equalsIgnoreCase("0-0"))) {
                itemThing += "Fire Damage:  " + fireDamage  + "\n";
            }
            if (airDamage != null && !(airDamage.equalsIgnoreCase("0-0"))) {
                itemThing += "Air Damage: " + airDamage + "\n";
            }
        }

        itemThing += "\nLevel: " + level;
        itemThing += "\n";
        //class req
        if (classRequirement != null) {
            itemThing += "Class Req: " + classRequirement
                    +"\n";
        }
        //quest req lmao this is waste
        if (quest != null) {
            itemThing += "Quest Req: " + quest
                    +"\n";
        }

        itemThing += "\n";

        //skillpoint reqs
        if (strength != 0) {
            itemThing += "Strength Min: " + strength
                    +"\n";
        }
        if (dexterity != 0) {
            itemThing += "Dexterity Min: " + dexterity
                    +"\n";
        }
        if (intelligence != 0) {
            itemThing += "Intelligence Min: " + intelligence
                    +"\n";
        }
        if (defense != 0) {
            itemThing += "Defense Min: " + defense
                    +"\n";
        }
        if (agility != 0) {
            itemThing += "Agility Min: " + agility
                    +"\n";
        }

        itemThing += "\n";


        //now for actual identifications
        if (healthRegen != 0) { //regens
            itemThing += "Health Regen: " + maxMinValues(healthRegen, "%") + "\n";
        }
        if (healthRegenRaw != 0) {
            itemThing += "Health Regen: " + maxMinValues(healthRegenRaw, "") +"\n";
        }
        if (manaRegen != 0) {
            itemThing += "Mana Regen: " + maxMinValues(manaRegen, "/4s") + "\n";
        } //stealing stats
        if (lifeSteal != 0) {
            itemThing += "Life Steal: " + maxMinValues(lifeSteal, "/4s") + "\n";
        }
        if (manaSteal != 0) {
            itemThing += "Mana Steal: " + maxMinValues(manaSteal, "/4s") + "\n";
        }
        //health bonus
        if (healthBonus != 0) {
            itemThing += "Health: " + maxMinValues(healthBonus, "") + "\n";
        }
        //damage bonuses
        if (spellDamage != 0) {
            itemThing += "Spell Damage: " + maxMinValues(spellDamage, "%") + "\n";
        }
        if (spellDamageRaw != 0) {
            itemThing += "Spell Damage: " + maxMinValues(spellDamageRaw, "") + "\n";
        }
        if (damageBonus != 0) {
            itemThing += "Melee Damage: " + maxMinValues(damageBonus, "%") + "\n";
        }
        if (damageBonusRaw != 0) {
            itemThing += "Melee Damage: " + maxMinValues(damageBonusRaw, "") + "\n";
        } //elemental damages
        if (bonusEarthDamage != 0) {
            itemThing += "Earth Damage: " + maxMinValues(bonusEarthDamage, "%") + "\n";
        }
        if (bonusThunderDamage != 0) {
            itemThing += "Thunder Damage: " + maxMinValues(bonusThunderDamage, "%") + "\n";
        }
        if (bonusWaterDamage != 0) {
            itemThing += "Water Damage: " + maxMinValues(bonusWaterDamage, "%") + "\n";
        }
        if (bonusFireDamage != 0) {
            itemThing += "Fire Damage: " + maxMinValues(bonusFireDamage, "%") + "\n";
        }
        if (bonusAirDamage != 0) {
            itemThing += "Air Damage: " + maxMinValues(bonusAirDamage, "%") + "\n";
        }

        //sp bonuses
        if (strengthPoints != 0) {
            itemThing += "Strength: " + maxMinValues(strengthPoints, "") + "\n";
        }
        if (dexterityPoints != 0) {
            itemThing += "Dexterity: " + maxMinValues(dexterityPoints, "") + "\n";
        }
        if (intelligencePoints != 0) {
            itemThing += "Intelligence: " + maxMinValues(intelligencePoints, "") + "\n";
        }
        if (defensePoints != 0) {
            itemThing += "Defense: " + maxMinValues(defensePoints, "") + "\n";
        }
        if (agilityPoints != 0) {
            itemThing += "Agility: " + maxMinValues(agilityPoints, "") + "\n";
        }

        //bonuses
        if (xpBonus != 0) {
            itemThing += "XP Bonus: " + maxMinValues(xpBonus, "%") + "\n";
        }
        if (lootBonus != 0) {
            itemThing += "Loot Bonus: " + maxMinValues(lootBonus, "%") + "\n";
        }

        //weird stats
        if (thorns != 0) {
            itemThing += "Thorns: " + maxMinValues(thorns, "%") + "\n";
        }
        if (reflection != 0) {
            itemThing += "Reflection: " + maxMinValues(reflection, "%") + "\n";
        }
        if (poison != 0) {
            itemThing += "Poison: " + maxMinValues(poison, "/3s") + "\n";
        }
        if (attackSpeedBonus != 0) {
            itemThing += "Attack Speed: " +maxMinValues(attackSpeedBonus, " tier") + "\n";
        }
        if (knockback != 0) {
            itemThing += "Knockback: " +maxMinValues(attackSpeedBonus, "%") + "\n";
        }
        if (exploding != 0) {
            itemThing += "Exploding: " + maxMinValues(exploding, "%") + "\n";
        }
        if (speed != 0) {
            itemThing += "Walk Speed: " + maxMinValues(speed, "%") + "\n";
        }
        if (soulPoints != 0) {
            itemThing += "Soul Point Regen: " + maxMinValues(soulPoints, "%") + "\n";
        }
        if (emeraldStealing != 0) {
            itemThing += "Stealing: " + maxMinValues(emeraldStealing, "%") + "\n";
        }

        //elemental defense
        if (bonusEarthDefense != 0) {
            itemThing += "Earth Defense: " + maxMinValues(bonusEarthDefense, "%") + "\n";
        }
        if (bonusThunderDefense != 0) {
            itemThing += "Thunder Defense: " + maxMinValues(bonusThunderDefense, "%") + "\n";
        }
        if (bonusWaterDefense != 0) {
            itemThing += "Water Defense: " + maxMinValues(bonusWaterDefense, "%") + "\n";
        }
        if (bonusFireDefense != 0) {
            itemThing += "Fire Defense: " + maxMinValues(bonusFireDefense, "%") + "\n";
        }
        if (bonusAirDefense != 0) {
            itemThing += "Air Defense: " + maxMinValues(bonusAirDefense, "%") + "\n";
        }

        itemThing += "\n";

        //powder slot display thing
        if (sockets > 0) {
            itemThing += sockets + " Powder Slots" + "\n";
            itemThing += "\n";
        }

        itemThing += "\n```";


        //lore
        if (addedLore != null) {
            itemThing += "\n```";
            itemThing += "\nLore";
            itemThing += "\n\n";
            itemThing += addedLore;
            itemThing+= "\n```";
        }




        return itemThing;
    }
    /*
    Calculates max and min values of an identification and returns it as a string for the above thing
     */
    private String maxMinValues(int n, String suffix) {
        int max, min;
        float base = n;
        String value = "";

        if (!identified) {

            if (base > 1) { //cuz negative and positive bases have different ones ya know
                max = (int) Math.round(base * MAX_ID);
                min = (int) Math.round(base * MIN_POS_ID);
            } else if (base == 1) {
                max = 1;
                min = 1;
            }
            else {
                max = (int) Math.round(base * MIN_NEG_ID);
                min = (int) Math.round(base * MAX_ID);
            }

            value = min + suffix + " to " + max + suffix;
            return value;
        } else {
            value = "" + (int)base + suffix;

        }
        return value;
    }

}
