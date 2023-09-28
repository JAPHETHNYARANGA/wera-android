package com.werrah.wera.data.LocationData

enum class Location(val displayName: String, val sublocations: List<String>) {
    KAJIADO("Kajiado", listOf(
        "All Kajiado", "Kitengela", "Ngong", "Ongata Rongai", "Bisil", "Dalalekutuk", "Emurua Dikir", "Entonet/Lenkisim",
        "Eselenkei", "Ewuaso Onnkidong'l", "Ildamat", "Iloodokilani/Amboseli", "Imaroro", "Isinya", "Kajiado CBD", "Kenyawa-Poka",
        "Kimana", "Kisaju", "Kiserian", "Kuku", "Kumpa", "Loitoktok", "Magadi", "Mata", "Matapato", "Mbirikani", "Mosiro", "Namanga", "Olkeri",
        "Oloika", "Oloolua", "Oloosirkon/Sholinke", "Purko", "Rombo",
    )),
    KIAMBU("Kiambu", listOf(
        "All Kiambu", "Juja", "Kiambu/Kiambu", "Kikuyu", "Ruiru", "Thika", "Banana", "Gachie",
        "Gatundu North", "Gatundu South", "Gitaru", "Githunguri", "Kabete", "Kiambaa", "Lari", "Limuru",
        "Nachu", "Ndereru", "Nyadhuna", "Rosslyn", "Ruaka", "Tuiritu", "Witeithie"
    )),
    MOMBASA("Mombasa", listOf(
        "All Mombasa", "Kisauni", "Mombasa CBD", "Mvita", "Nyali", "Tudor", "Bamburi", "Chaani",
        "Changamwe", "Ganjoni", "Industrial Area(Msa)", "Jomvu", "Kikowani", "Kizingo", "Likoni", "Makadara(Msa)",
        "Mbaraki", "Old Town", "Shanzu", "Shimanzi", "Tononoka"
    )),
    NAIROBI("Nairobi", listOf(
        "Embakasi", "Karen", "Kilimani", "Nairobi Central", "Ngara", "Airbase", "Baba Dogo", "Califonia", "Chokaa", "Clay City", "Dagoretti", "Dandora", "Donholm", "Eastleigh", "Gikomba/Kamukunji"
        , "Githurai", "Huruma", "Imara Daima", "Industrial Area Nairobi", "Jamhuri", "Kabiro", "Kahawa West", "Kamulu", "Kangemi", "Kariobangi", "Kasarani", "Kawangware", "Kayole", "Kiamaiko"
        , "Kibra", "Kileleshwa", "Kitisuru", "Komarock", "Landimawe", "Langata", "Lavington", "Lucky Summer", "Makadara", "Makongeni", "Maringo/Hamza", "Mathare Hospital", "Mathare North", "Mbagathi Way"
        , "Mlango Kubwa", "Mombasa Road", "Mountain View", "Mowlem", "Muthaiga", "Mwiki", "Nairobi South", "Nairobi West", "Njiru", "Pangani", "Parklands/Highridge", "Pumwani", "Ridgeways", "Roysambu", "Ruai"
        , "Ruaraka", "Runda", "Saika", "South B", "South C", "Thome", "Umoja", "Upperhill", "Utalii", "Utawala", "Westlands", "Woodley/ Kenyatta Golf Course", "Zimmerman", "Ziwani/Kariokor"
    )),
    KISUMU("Kisumu", listOf(
        "All Kisumu", "Kisumu Central", "Kisumu West", "Chemelil", "Kaloleni", "Kisumu East", "Kolwa Central", "Kolwa East", "Muhoroni", "North West Kisumu", "Nyakach",
        "Nyando", "Seme", "South West Kisumu"
    ))
}
