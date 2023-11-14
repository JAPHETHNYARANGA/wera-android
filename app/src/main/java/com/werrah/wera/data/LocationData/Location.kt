package com.werrah.wera.data.LocationData


enum class Location {
    Kajiado, Kiambu, Mombasa, Nairobi, Nakuru, Baringo, Bomet, Bungoma, Busia, ElgeyoMarakwet, Embu,
    Garisa, HomaBay, Isiolo, Kakamega, Kericho, Kilifi, Kirinyaga, Kisii, Kisumu, Kitui, Kwale, Laikipia, Lamu, Machakos, Makueni, Mandera, Marsabit, Meru,
    Migori, Muranga, Nandi, Narok, Nyamira, Nyandarua, Nyeri, Samburu, Siaya, TaitaTaveta, TanaRiver, TharakaNithi, TransNzoia, Turkana, UasinGishu, Vihiga, Wajir, WestPokot
    // Add more locations as needed
}

enum class SubLocation(val location: Location, val subLocations: List<String>) {
    Kajiado(
        Location.Kajiado,
        listOf(
            "All Kajiado", "Kitengela","Ngong","Ongata Rongai","Bisil","Dalalekutuk","Emurua Dikir","Entonet/Lenkisim",
            "Eselenkei","Ewuaso Onnkidong'l","Ildamat","Iloodokilani/Amboseli","Imaroro","Isinya","Kajiado CBD","Kenyawa-Poka",
            "Kimana","Kisaju","Kiserian","Kuku","Kumpa","Loitoktok","Magadi","Mata","Matapato","Mbirikani","Mosiro","Namanga","Olkeri",
            "Oloika","Oloolua","Oloosirkon/Sholinke","Purko","Rombo",
        )
    ),
    Kiambu(
        Location.Kiambu,
        listOf(
            "All Kiambu", "Juja","Kiambu/Kiambu","Kikuyu","Ruiru","Thika","Banana","Gachie",
            "Gatundu North","Gatundu South","Gitaru","Githunguri","Kabete","Kiambaa","Lari","Limuru",
            "Nachu","Ndereru","Nyadhuna","Rosslyn","Ruaka","Tuiritu","Witeithie"
        )
    ),
    Mombasa(
        Location.Mombasa,
        listOf(
            "All Mombasa", "Kisauni","Mombasa CBD","Mvita","Nyali","Tudor","Bamburi","Chaani",
            "Changamwe","Ganjoni","Industrial Area(Msa)","Jomvu","Kikowani","Kizingo","Likoni","Makadara(Msa)",
            "Mbaraki","Old Town","Shanzu","Shimanzi","Tononoka"
        )
    ),
    Nairobi(
        Location.Nairobi,
        listOf(
            "Embakasi","Karen","Kilimani","Nairobi Central","Ngara","Airbase","Baba Dogo","Califonia","Chokaa","Clay City","Dagoretti","Dandora","Donholm","Eastleigh","Gikomba/Kamukunji"
            ,"Githurai","Huruma","Imara Daima","Industrial Area Nairobi","Jamhuri","Kabiro","Kahawa West","Kamulu","Kangemi","Kariobangi","Kasarani","Kawangware","Kayole","Kiamaiko"
            ,"Kibra","Kileleshwa","Kitisuru","Komarock","Landimawe","Langata","Lavington","Lucky Summer","Makadara","Makongeni","Maringo/Hamza","Mathare Hospital","Mathare North","Mbagathi Way"
            ,"Mlango Kubwa","Mombasa Road","Mountain View","Mowlem","Muthaiga","Mwiki","Nairobi South","Nairobi West","Njiru","Pangani","Parklands/Highridge","Pumwani","Ridgeways","Roysambu","Ruai"
            ,"Ruaraka","Runda","Saika","South B","South C","Thome","Umoja","Upperhill","Utalii","Utawala","Westlands", "Woodley/ Kenyatta Golf Course","Zimmerman","Ziwani/Kariokor"
        )
    ),
    Nakuru(
        Location.Nakuru,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Baringo(
        Location.Baringo,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Bomet(
        Location.Bomet,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Bungoma(
        Location.Bungoma,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Busia(
        Location.Busia,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    ElgeyoMarakwet(
        Location.ElgeyoMarakwet,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Embu(
        Location.Embu,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Garisa(
        Location.Garisa,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    HomaBay(
        Location.HomaBay,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Isiolo(
        Location.Isiolo,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Kakamega(
        Location.Kakamega,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Kericho(
        Location.Kericho,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Kilifi(
        Location.Kilifi,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Kirinyaga(
        Location.Kirinyaga,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Kisii(
        Location.Kisii,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Kisumu(
        Location.Kisumu,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Kitui(
        Location.Kitui,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Kwale(
        Location.Kwale,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Laikipia(
        Location.Laikipia,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Lamu(
        Location.Lamu,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Machakos(
        Location.Machakos,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Makueni(
        Location.Makueni,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Mandera(
        Location.Mandera,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Marsabit(
        Location.Marsabit,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Meru(
        Location.Meru,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Migori(
        Location.Migori,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Muranga(
        Location.Muranga,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Nandi(
        Location.Nandi,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Narok(
        Location.Narok,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Nyamira(
        Location.Nyamira,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Nyandarua(
        Location.Nyandarua,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Nyeri(
        Location.Nyeri,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Samburu(
        Location.Samburu,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Siaya(
        Location.Siaya,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    TaitaTaveta(
        Location.TaitaTaveta,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    TanaRiver(
        Location.TanaRiver,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    TharakaNithi(
        Location.TharakaNithi,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    TransNzoia(
        Location.TransNzoia,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Turkana(
        Location.Turkana,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    UasinGishu(
        Location.UasinGishu,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Vihiga(
        Location.Vihiga,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    Wajir(
        Location.Wajir,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    ),
    WestPokot(
        Location.WestPokot,
        listOf("SubLocation1", "SubLocation2", "SubLocation3")
    )

//    companion object {
//        fun getSubLocations(location: Location): List<String>? {
//            return values().find { it.location == location }?.subLocations
//        }
//    }
}
