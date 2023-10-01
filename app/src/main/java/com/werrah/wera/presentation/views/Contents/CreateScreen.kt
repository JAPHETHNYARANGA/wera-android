package com.werrah.wera.presentation.views.Contents

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.werrah.wera.domain.models.CategoriesData
import com.werrah.wera.presentation.viewModel.GetCategoriesViewModel
import com.werrah.wera.presentation.viewModel.GetListingsViewModel
import com.werrah.wera.presentation.viewModel.GetUserListingsViewModel
import com.werrah.wera.presentation.viewModel.PostItemViewModel
import com.werrah.wera.presentation.views.shared.LoadingSpinner
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.storage.FirebaseStorage
import com.werrah.wera.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CreateScreen(
    postItemViewModel: PostItemViewModel,
    navController: NavController,
    getListingsViewModel: GetListingsViewModel,
    getUserListingsViewModel : GetUserListingsViewModel,
    getCategoriesViewModel : GetCategoriesViewModel
){

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var sublocation by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(0) }
    val context = LocalContext.current

    var isExpanded by remember {
        mutableStateOf(false)
    }
    var isExpandedStatus by remember {
        mutableStateOf(false)
    }
    var status by remember{
        mutableStateOf(0)
    }

    var isExpandedStatus1 by remember { mutableStateOf(false) }
    var isExpandedSubLocation by remember { mutableStateOf(false) }
    var selectedCity by remember { mutableStateOf<String?>(null) }
    var selectedSubCity by remember { mutableStateOf<String?>(null) }

    var selectedCategory by remember { mutableStateOf<CategoriesData?>(null) }
    // Observe the categories data from the view model
    val categoriesData by getCategoriesViewModel.categoriesDisplay.collectAsState()

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }
    var isLoading by remember { mutableStateOf(false) }
    var isPosting by remember { mutableStateOf(false) }


    fun statusToString(status: Int): String {
        return when (status) {
            1 -> "Post Listing"
            2 -> "Post My Skill"
            else -> "" // Handle the default case or any other status values you might have
        }
    }

    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
        ) {

            Row(
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column {
                    // Show the image with the "+" icon below it
                    imageUri?.let {
                        if (Build.VERSION.SDK_INT < 28) {
                            bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                        } else {
                            val source = ImageDecoder.createSource(context.contentResolver, it)
                            bitmap.value = ImageDecoder.decodeBitmap(source)
                        }

                        bitmap.value?.let { btm ->
                            ItemImage(
                                bitmap = btm,
                                contentDescription = null,
                                modifier = Modifier // Provide the correct type of the modifier here
                            )
                        }
                    }


                    Spacer(modifier = Modifier.height(10.dp))
                    IconButton(
                        onClick = {
                            launcher.launch("image/*")
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.add),
                            contentDescription = "Add Image"
                        )
                    }
                }
            }


            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Task Name") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF1A202C),
                    unfocusedBorderColor = Color(0xFF1A202C)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF1A202C),
                    unfocusedBorderColor = Color(0xFF1A202C)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Text(
                text = "Location", // Add the label text here
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
            )

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                // Align the ExposedDropdownMenuBox to center
                ExposedDropdownMenuBox(
                    expanded = isExpandedStatus1,
                    onExpandedChange = { isExpandedStatus1 = it },
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    OutlinedTextField(
                        value = TextFieldValue(text = selectedCity ?: "Select a city"),
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedStatus1)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = isExpandedStatus1,
                        onDismissRequest = { isExpandedStatus1 = false }
                    ) {
                        val cities = listOf(
                            "Kajiado", "Kiambu", "Mombasa", "Nairobi", "Nakuru", "Baringo", "Bomet", "Bungoma", "Busia", "ElgeyoMarakwet", "Embu",
                            "Garisa", "HomaBay", "Isiolo", "Kakamega", "Kericho", "Kilifi", "Kirinyaga", "Kisii", "Kisumu", "Kitui", "Kwale", "Laikipia", "Lamu", "Machakos", "Makueni", "Mandera", "Marsabit", "Meru",
                            "Migori", "Muranga", "Nandi", "Narok", "Nyamira", "Nyandarua", "Nyeri", "Samburu", "Siaya", "Taita-Taveta", "Tana-River", "Tharaka-Nithi", "Trans-Nzoia", "Turkana", "Uasin-Gishu", "Vihiga", "Wajir", "West-Pokot"
                        )

                        cities.forEach { city ->
                            DropdownMenuItem(
                                text = { Text(city) },
                                onClick = {
                                    selectedCity = city // Store the selected city as text
                                    location = city
                                    isExpandedStatus1 = false
                                },
                            )
                        }
                    }
                }
            }

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                // Align the ExposedDropdownMenuBox to center
                ExposedDropdownMenuBox(
                    expanded = isExpandedSubLocation,
                    onExpandedChange = { isExpandedSubLocation = it },
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    OutlinedTextField(
                        value = TextFieldValue(text = selectedSubCity ?: "Select sub city"),
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedSubLocation)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = isExpandedSubLocation,
                        onDismissRequest = { isExpandedSubLocation = false }
                    ) {
                        when (selectedCity) {
                            "Kajiado" -> {
                                val Kajiado = listOf(
                                    "All Kajiado", "Kitengela","Ngong","Ongata Rongai","Bisil","Dalalekutuk","Emurua Dikir","Entonet/Lenkisim",
                                    "Eselenkei","Ewuaso Onnkidong'l","Ildamat","Iloodokilani/Amboseli","Imaroro","Isinya","Kajiado CBD","Kenyawa-Poka",
                                    "Kimana","Kisaju","Kiserian","Kuku","Kumpa","Loitoktok","Magadi","Mata","Matapato","Mbirikani","Mosiro","Namanga","Olkeri",
                                    "Oloika","Oloolua","Oloosirkon/Sholinke","Purko","Rombo",
                                )
                                Kajiado.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Kiambu" -> {
                                val Kiambu = listOf(
                                    "All Kiambu", "Juja","Kiambu/Kiambu","Kikuyu","Ruiru","Thika","Banana","Gachie",
                                    "Gatundu North","Gatundu South","Gitaru","Githunguri","Kabete","Kiambaa","Lari","Limuru",
                                    "Nachu","Ndereru","Nyadhuna","Rosslyn","Ruaka","Tuiritu","Witeithie"
                                )
                                Kiambu.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Mombasa" -> {
                                val Mombasa = listOf(
                                    "All Mombasa", "Kisauni","Mombasa CBD","Mvita","Nyali","Tudor","Bamburi","Chaani",
                                    "Changamwe","Ganjoni","Industrial Area(Msa)","Jomvu","Kikowani","Kizingo","Likoni","Makadara(Msa)",
                                    "Mbaraki","Old Town","Shanzu","Shimanzi","Tononoka"
                                )
                                Mombasa.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Nairobi" -> {
                                val Nairobi = listOf(
                                    "Embakasi","Karen","Kilimani","Nairobi Central","Ngara","Airbase","Baba Dogo","Califonia","Chokaa","Clay City","Dagoretti","Dandora","Donholm","Eastleigh","Gikomba/Kamukunji"
                                    ,"Githurai","Huruma","Imara Daima","Industrial Area Nairobi","Jamhuri","Kabiro","Kahawa West","Kamulu","Kangemi","Kariobangi","Kasarani","Kawangware","Kayole","Kiamaiko"
                                    ,"Kibra","Kileleshwa","Kitisuru","Komarock","Landimawe","Langata","Lavington","Lucky Summer","Makadara","Makongeni","Maringo/Hamza","Mathare Hospital","Mathare North","Mbagathi Way"
                                    ,"Mlango Kubwa","Mombasa Road","Mountain View","Mowlem","Muthaiga","Mwiki","Nairobi South","Nairobi West","Njiru","Pangani","Parklands/Highridge","Pumwani","Ridgeways","Roysambu","Ruai"
                                    ,"Ruaraka","Runda","Saika","South B","South C","Thome","Umoja","Upperhill","Utalii","Utawala","Westlands", "Woodley/ Kenyatta Golf Course","Zimmerman","Ziwani/Kariokor"
                                )
                                Nairobi.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Kisumu" -> {
                                val Kisumu = listOf("All Kisumu", "Kisumu Central", "Kisumu West"," Chemelil","Kaloleni","Kisumu East","Kolwa Central","Kolwa East", "Muhoroni","North West Kisumu","Nyakach",
                                    "Nyando","Seme","South West Kisumu")
                                Kisumu.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Nakuru" -> {
                                val Nakuru = listOf("All Nakuru","Nakuru Town East","Naivasha","Bahati","Gilgil","Hells Gate","Kuresoi North","Kuresoi South","Lanet","London","Maiella","Malewa West","Mbaruk/Eburu","Molo","Nakuru Town West",
                                "Njoro","Olkaria", "Rongai","Salgaa","Subukia")
                                Nakuru.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Baringo" -> {
                                val Baringo = listOf("All Baringo","Kabarnet","Marigat","Ravine","Bartabwa","Barwessa","Churo/Amanya","Emining","Ewalel Chapchap","Kapropita","Kisanana","Koibatek","Lembus","Lembus Kwen","Lembus /Perkerra",
                                "Mogotio","Mukutani","Nginyang East","Nginyang West","Sacho", "Silale","Tenges")
                                Baringo.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Bomet" -> {
                                val Bomet = listOf("All Bomet","Chemagei","Longisa","Silibwet TownShip","Boito","Chebunyo","Embomos","Kapletundo","Kembu","Kimulot","Kipsonoi","Merigi","Magogosiek","Mutarakwa","Ndanai/ Abosi",
                                "Ndaraweta","Nyongores","Sigor","Singorwet","Siongiroi")
                                Bomet.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Bungoma" -> {
                                val Bungoma = listOf("All Bungoma","Kabuchai/Chwele","Khalaba/Kanduyi","Marakaru/Tuuti","Township D","Bokoli","Bukembe East","Bukembe West","Bumula","Bwake/Luuya","Cheptais","Chepyuk","East Sang'Alo","Elgon","Kabula","Kamukuywa","Kibingei","Kimaeti"
                                ,"Kimilili","Maeni","Malakisi/South Kulisiru","Maraka","Matulo","Mihuu","Misikhu","Mukisoma","Namwela", "Ndalu/Tabani","Siboti","Sitikho","South Bukusu","Soysambu/ Mitua","Tongeren","Webuye","West Bukusu","West Nalondo","West Sang'Alo")
                                Bungoma.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Busia" -> {
                                val Busia = listOf("All Busia","Angeng'A Nanbuga","Bunyala West/Budalangi","Burumba","Amukura Central","Amukura East","Amukura West","Ang'Orom","Bukhayo Central","Bukhayo East","Bukhayo West","Bukhayo North","Busiwabo","Bwiri","Elugulu","Kingandole","Malaba Central","Malaba North","Marachi Central","Marachi East","Marachi West","Matayos South",
                                "Manyenje","Nambale Township","Nangina")
                                Busia.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "ElgeyoMarakwet" -> {
                                val ElgeyoMarakwet = listOf("All Elgeyo-Marakwet","Arror","Chepkorio","Emsoo","Endo","Kamariny","Kapchemutwa","Kaptarakwa","Metkei","Moiben/Kuserwo","Sengwer","Soy North","Tambach")
                                ElgeyoMarakwet.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Embu" -> {
                                val Embu = listOf("All Embu","Central Ward","Kirimari","Mwea","Evurore","Gaturi North","Gaturi South","Kagaari North","Kagaari South","Kiambere","Kithimu","Kyeni North","Kyeni South","Makima","Mavuria","Mbeti North","Mbeti South","Muminji","Nginda","Nthawa","Ruguru/Ngandori")
                                Embu.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Garissa" -> {
                                val Garissa = listOf("All Garissa","Dadaab","Iftin","Abakaile","Bura","Demajale","Galbet","Goreale","Ijara","Jara Jara","Labasigale","Liboi","Masalani","Sankuri","Waberi")
                                Garissa.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "HomaBay" -> {
                                val HomaBay = listOf("All HomaBay","Homa Bay Central","Mfangano Island","Cenral Karachuonyo","Central Kasipul","East Gem(Rangwe)","East Kamagak","Gembe","Homa Bay Arujo","Homa Bay East","Homa Bay West","Kabondo East","Kagan","Kaksingri West","Kanyadoto","Kanyaluo",
                                "Kanyamwa Kosewe","Kanyikela","Kasgunga","Kendu Bay Town","Kochia","Kwabwai","Lambwe","North Karachuonyo","Rusinga Island","South Kasipul","West Kasipul")
                                HomaBay.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Isiolo" -> {
                                val Isiolo = listOf("All Isiolo","Bulla Pesa","Burat","Garba Tulla","Isiolo North","Kinna","Oldonyiro","Wabera")
                                Isiolo.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Kakamega" -> {
                                val Kakamega = listOf("All Kakamega","Mumias Central","Sheywe","Bunyala Central(Navakholo)","Butali/Chegulo","Butsotso Central","Butsotso Central","Butsotso East","Butsotso South","Chekalini","Chemuche","Chevaywa",
                                "East Kabras","East Wanga","Etenje","Idakho Central","Idakho East","Idhakho South","Ingostse-Mathia","Isukha Central","Isukha East","Isukha North","Isukha South","Isukha West","Khalaba(Matungu)","Kholera","Kisa Central","Kisa East","Kisa North",
                                    "Kisa West","Kongoni","Koyonzo","Likunyani","Lugari","Lumakanda","Lusheya/Lubinu","Lwandeti","Mahiakalo","Mulaha/Isongo/Makunga","Marama Central","Marama North","Marama South","Marama West","Marenyo-Shianda","Mautuma","Mayoni","Murhanda","Musanda","Nzoia","Sango","Shinyoli-Shikomari-Esumeyia",
                                "Shirere","Shirigu-Mugai","Sinoko","South Kabras","West Kabras")
                                Kakamega.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Kericho" -> {
                                val Kericho = listOf("All Kericho","Ainamoi","Litein","Chaik","Cheboin","Chemosot","Cheplanget","Chepseon","Cheptororiet/Seretut","Chilchila","Kabianga","Kamasian","Kapkatet","Kapkugerwet","Kapsaos","Kapsoit","Kapsuser","Kedowa/Kimigul",
                                "Kipchebor","Kipchimchim","Kipkelion","Londiani","Sigowet","Soin","Tebesonik","Waldai")
                                Kericho.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Kilifi" -> {
                                val Kilifi = listOf("All Kilifi","Kilifi North","Malindi","Mtwapa","Ganze","Kaloleni","Kambe/Ribe","Kilifi South","Kilifi Town","Magarini","Rabai")
                                Kilifi.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Kirinyaga" -> {
                                val Kirinyaga = listOf("All Kirinyaga","Kerugoya","Kiine","Baragwi","Gathigiriri","Inoi","Kabare","Kangai","Kanyekini","Kariti","Karumandi","Mukure","Marinduko","Mutira","Mutithi","Ngariama","Njukiini","Nyagati",
                                "Tebere","Thiba","Wamumu")
                                Kirinyaga.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Kisii" -> {
                                val Kisii = listOf("All Kisii","Kisii CBD","Basi Bogetaorio","Bobasi","Bomachoge Borabu","Bomachoge Chache","Bonchari","Kitutu Chahe South","Marani","Monyerero","Nyaribari Chache","Nyaribari Masaba","Ogembo","South Mugirango","Suneka")
                                Kisii.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Kitui" -> {
                                val Kitui = listOf("All Kitui","Central Mwingi","TownShip","Athi","Chuluni","Endau/Malalani","Ikanga/Kyatune","Kanyagi","Kauwi","Kiomo/Kyeni","Kisasi","Kwa Mutonga/Kithumula","Kwavonza/Yatta","Kyangwithya East","Kyangwithya West",
                                "Kyome/Thaana","Kyuso","Matinyani","Mbitini","Migwani","Mulango","Mutito/Kaliku","Mutomo","Mutonguni","Ngomeni","Nguni","Nguutani","Nzambani","Tseikuru","Zombe/Mwitika")
                                Kitui.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Kwale" -> {
                                val Kwale = listOf("All Kwale","Ukunda","Chengoni/Samburu","Dzombo","Gombato Bongwe","Kinango","Kinondo","Kubo South","Mackinnon Road","Mazeras","Mkongani","Mwavumbo","Mwereni","Ndavya","Pongwe/Kikoneni","Ramisi","Tiwi","Tsimba Golini",
                                "Vanga","Waa")
                                Kwale.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Laikipia" -> {
                                val Laikipia = listOf("All Laikipia","Nanyuki","Githiga", "Laikipia Central"," Laikipia East","Laikipia North","Laikipia West","Mukogondo East","Nyahururu")
                                Laikipia.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Lamu" -> {
                                val Lamu = listOf("All Lamu","Mkomani","Bahari","Faza","Hindi","Hongwe","Shella","Witu")
                                Lamu.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Machakos" -> {
                                val Machakos = listOf("All Machakos","Athi River","Machakos Town","Syokimau","Kangundo","Kathiani","Lower Kaewa/Kaani","Masinga","Matungulu","Mavoko","Mlolongo","Mua","Mwala","Upper Kewa/Iveti","Yatta")
                                Machakos.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Makueni" -> {
                                val Makueni = listOf("All Makueni","Emali/Mulala","Makindu","Wote","Ilima","Ivingoni/Nzambani","Kasikeu","Kathonzweni","Kiima Kiu/Kalanzoni","Kimbulyu North","Kikumbulyu South","Kilungu","Kisau-Kiteta","Kithungo/Kitundu","Masongaleni","Mavindini","Mbitini","Mbooni","Mtito Andei","Mukaa",
                                "Muvau/Kikuumini","Nguu/Masumba","Nguumo","Nzaui/Kilili/Kalamba","Thange","Tulimani","Ukia","Waia/Kako")
                                Makueni.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Mandera" -> {
                                val Mandera = listOf("All Mandera","Elwak North","Township")
                                Mandera.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Marsabit" -> {
                                val Marsabit = listOf("All Marsabit","Butiye","Marsabit Central","Moyale Township")
                                Marsabit.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Meru" -> {
                                val Meru = listOf("All Meru","Maua","Municipality","Abogeta East","Abogeta West","Abothuguchi Central","Akirang'Ondu","Akithii","Amwathi","Antubetwe Kidogo","Athiru Gaiti","Athiru Ruujine","Athwana","Igembe East","Igoji East","Igoji West",
                                "Kangeta","Karama","Kiagu","Kianjai","Kibirichia","Kiirua/Naari","Kisima","Mbeu","Mikinduri","Mitunguu","Muthara","Mwanganthia","Nkomo","Nkuene","Ntima East","Ntima West","Nyaki East","Nyaki West","Ruiri/Rwarera","Timau")
                                Meru.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Migori" -> {
                                val Migori = listOf("All Migori","Bukira Central/Ikerenge","Bukira East","Central Kamagombo","Central Kanyamkago","Central Sakwa(Awendo)","East Kamagambo","East Kanyamkago","God Jope","Isbania","Kachien'GA","Kakrao","Kwa","Makerero","Muhuru","North Kadem","North Kamagambo","North Sakwa","Ntimaru West","Ragana-Oruba","South Kamagambo","South Sakawa","Suna Central","Tagare","Wasweta","West Sakawa")
                                Migori.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Muranga" -> {
                                val Muranga = listOf("All Muranga","Gatanga","Kimorori/Wempa","Makuyu","Township G","Gaichanjiru","Gaturi","Gitugi","Ichagaki","Ithanga","Ithiru","Kangudu-ini","Kahumbu","Kakuzi/Mitubiri","Kamacharia","Kamahuha","Kambiti","Kangari","Kanyenya-ini","Kariara","Kiguno","Kihumbu-ini","Kinyona","Kiru","Mbiri","Mugoiri","Mugumo-Ini","Muguru","Muruka","Ngararia","Nginda","Ruchu","Rwathia","Wangu")
                                Muranga.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Nandi" -> {
                                val Nandi= listOf("All Nandi","Kapsabet","Chemelil/Chemase","Chemundu/Kapng'etuny","Chepkumia","Chepkunyuk","Kabisaga","Kibiyet","Kapkangani","Kaptel/Kamaiywo","Kaptumo-Kaboi","Kimeloi-Maraba","Kilibwoni","Kipkaren","Kiptuya","Kobujoi","Kosirai","Nandi Hills","Ndalat","Ol'Lessos","Songhor/Soba","Tindiret")
                                Nandi.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Narok" -> {
                                val Narok= listOf("All Narok","Narok Town","Angata Barikoi"," Ildamat(Narok)","Ilkerin","IlMotiok","Kapsasian","Keekonyokie(Narok)","Keyian","Kilgoris Central","Kimintet","Lolgorian","Majimoto/Naroosura","Mara","Melelo","Melili","Mosiro",
                                "Naikarra","Olokurto","Ololmasani","Olulung'A","Olorropin","Olpusimoru","Siana","Suswa")
                                Narok.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Nyamira" -> {
                                val Nyamira= listOf("All Nyamira","Bogichora","Bokeira","Bonyamatuta","Esise","Gachuba","Gesima","Kemera","Kiabonyoru","Magwagwa","Manga","Nyamaiya","Nyansiogo","Rigoma","Township F")
                                Nyamira.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Nyandarua" -> {
                                val Nyandarua= listOf("All Nyandarua","Karau","Magumu","Central Ndaragwa","Charagita","Engineer","Gathanji","Gathana","Gatimu","Geta","Githabai","Githioro","Kaimbaga","Kanjuiri Range","Kipipiri","Kiriita","Leshau Pondo","Mirangine","Murungaru","NjabiniKiburu","North Kinangop","Nyakio","Rurii","Shamata","Wanjohi","Weru" )
                                Nyandarua.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Nyeri" -> {
                                val Nyeri= listOf("All Nyeri","Karatina Town","Dedan Kimathi","Iriani","Kieni","Karimukuyu","Mathira","Mukurwe-ini-central","mukurwe-ini-west","Mukurweini","Naromoru Kiamathaga","Nyeri Town","Othaya","Tetu","Thegu River" )
                                Nyeri.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Samburu" -> {
                                val Samburu= listOf("All Samburu","Angata Nanyokie","Loosuk","Maralal","Suguta Marmar","Wamba East","Waso" )
                                Samburu.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Siaya" -> {
                                val Siaya= listOf("All Siaya","Siaya Township","Central Gem","Central Sakwa(Bondo)","East Asembo","East Gem","East Ugenya","North Gem","North Sakwa(Bondo)","North Ugenya","North Uyoma","Sidindi","Sigomere","South East Alego","South Gem","South Sakwa(Bondo)","Ugunja","Ukwala","West Alego","West Asembo","West Gem","West Sakwa(Bondo)","West Ugenya","West Uyoma","Yala Township","Yimbo West" )
                                Siaya.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Taita-Taveta" -> {
                                val TaitaTaveta= listOf("All Taita-Taveta","Bura(Mwatate)","Chala","Kaloleni","Kasigau","Mahoo","Mata","Mboghoni","Mbololo","Mwatate","Ngolia","Sagala","Werugha","Wamingu/Kishushe","Wundanyi/Mbale")
                                TaitaTaveta.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Tana-River" -> {
                                val TanaRiver= listOf("All Tana-River","Bura","Chewani","Garsen Central","Kinakomba","Kipini East","Kipini West","Madogo","Mikinduni","Wayu")
                                TanaRiver.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Tharaka-Nithi" -> {
                                val TharakaNithi= listOf("All Tharaka-Nithi","Igambang'Ombe","Chiakariga","Chogoria","Ganga","Gatunga","Karigani","Magumoni","Mariani","Mitheru","Mugwe","Mukothima","Muthambi")
                                TharakaNithi.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Trans-Nzoia" -> {
                                val TransNzoia= listOf("All Trans-Nzoia","Kitale","Bidii","Chepchoina","Chepsiro/Kiptoror","Cherangany/Suwerwa","Endebess","Hospital(Kiminini","Kaplamai","Keiyo","Kiminini","Kinyoro","Kwanza","Makutano","Matisi","Motosiet","Nabiswa","Saboti","Sikhendu","Sinyerere","Sirende","Sitatunga","Tuwani","Waitaluk")
                                TransNzoia.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Turkana" -> {
                                val Turkana= listOf("All Turkana","Lodwar Township","Kakuma","Kanamkemer","Katilia","Kerio Delta","Kibish","Lokichar","Lokichoggio","Turkwel")
                                Turkana.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Uasin-Gishu" -> {
                                val UasinGishu= listOf("All Uasin-Gishu","Eldoret CBD","Ainabkoi","Kapseret","Kesses","Moinben","Soy","Turbo")
                                UasinGishu.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Vihiga" -> {
                                val Vihiga= listOf("All Vihiga","Cental Bunyore","Central Maragoli","Chavakali","Luanda Township","Banja","Emabungo","Luanda South","Lugaga-Wamuluma","Lyaduywa/Izava","Muhudu","Mwibona","North East Bunyore","North Maragoli","Shamakhokho","Shiru","Tambua","Wodanga")
                                Vihiga.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "Wajir" -> {
                                val Wajir = listOf("All Wajir","Barwago","Godoma","Habasswein","Korondile","Tarbaj","Township")
                                Wajir.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            "West-Pokot" -> {
                                val WestPokot= listOf("All West-Pokot","Endugh","Kapenguria","Lomut","Mnagei")
                                WestPokot.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(city) },
                                        onClick = {
                                            selectedSubCity = city // Store the selected city as text
                                            sublocation = city
                                            isExpandedSubLocation = false
                                        },
                                    )
                                }
                            }
                            else -> {
                                // Handle the case when no city is selected
                                Text("Select a city first")
                            }

                        }
                    }
                }
            }




            Text(
                text = "Categories", // Add the label text here
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
            )

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                // Align the ExposedDropdownMenuBox to center
                ExposedDropdownMenuBox(
                    expanded = isExpanded,
                    onExpandedChange = { isExpanded = it },
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    OutlinedTextField(
                        value = TextFieldValue(selectedCategory?.name ?: ""),
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = { isExpanded = false }
                    ) {
                        // Populate the dropdown menu with categories data
                        categoriesData.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category.name ?: "") },
                                onClick = {
                                    selectedCategory = category // Store the selected category data
                                    isExpanded = false
                                }
                            )
                        }
                    }
                }
            }


            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Budget") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF1A202C),
                    unfocusedBorderColor = Color(0xFF1A202C)
                ),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)

            )

            Button(
                onClick = {
                    if(!isPosting){
                        isPosting = true
                        CoroutineScope(Dispatchers.Main).launch {
                            try {
                                // Get a reference to Firebase Storage
                                val selectedFile = imageUri?.let { uri ->
                                    try {
                                        val inputStream = context.contentResolver.openInputStream(uri)
                                        val byteArray = inputStream?.readBytes()
                                        inputStream?.close()

                                        if (byteArray != null) {
                                            // Save the ByteArray to a temporary file
                                            val tempFile = File.createTempFile("temp_image", ".jpg", context.cacheDir)
                                            tempFile.writeBytes(byteArray)
                                            tempFile
                                        } else {
                                            null
                                        }
                                    } catch (e: Exception) {
                                        null
                                    }
                                }
                                if(name.isEmpty()){
                                    Toast.makeText(context, "Name cannot be empty", Toast.LENGTH_LONG).show()
                                }else if(description.isEmpty()){
                                    Toast.makeText(context, "Description cannot be empty", Toast.LENGTH_LONG).show()
                                }else if (location.isEmpty()){
                                    Toast.makeText(context, "Location cannot be empty", Toast.LENGTH_LONG).show()
                                }else if(sublocation.isEmpty()){
                                    Toast.makeText(context, "Sub Location cannot be empty", Toast.LENGTH_LONG).show()
                                }
                                else if (amount.isEmpty()){
                                    Toast.makeText(context, "Amount cannot be empty", Toast.LENGTH_LONG).show()
                                }


                                // Check if the Bitmap is not null
                                if (selectedFile != null) {
                                    // Convert Bitmap to ByteArray
                                    val requestBuilder = selectedFile?.let {
                                        RequestBody.create("image/*".toMediaTypeOrNull(),
                                            it
                                        )
                                    }
                                    // Create the MultipartBody.Part
                                    val imageBody = requestBuilder?.let {
                                        MultipartBody.Part.createFormData("profile_image", selectedFile.name,
                                            it
                                        )
                                    }
                                    // Get a reference to Firebase Storage
                                    val storage = FirebaseStorage.getInstance()
                                    val storageRef = storage.reference

                                    // Initialize shared preferences
                                    val userId = postItemViewModel.userId
                                    val uniqueId = UUID.randomUUID().toString()
                                    // Generate a unique name for the image file using the current timestamp
                                    val fileName = "item_${System.currentTimeMillis()}.jpg"
                                    val imageRef = storageRef.child("item_images/$userId/$uniqueId/$fileName")
                                    val image = "item_images/$userId/$uniqueId/$fileName"
                                    val existingImageRef = storageRef.child("item_images/$userId")

                                    existingImageRef.listAll().addOnSuccessListener { listResult ->
                                        val allTasks = mutableListOf<Task<Void>>()
                                        listResult.items.forEach { item ->
                                            val deleteTask = item.delete()
                                            allTasks.add(deleteTask)
                                        }

                                        Tasks.whenAllComplete(allTasks).addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                Log.d("FirebaseStorage", "All images inside folder deleted successfully")
                                                // Upload the image to Firebase Storage
                                                val uploadTask = imageRef.putFile(Uri.fromFile(selectedFile))

                                                uploadTask.continueWithTask { task ->
                                                    if (!task.isSuccessful) {
                                                        task.exception?.let {
                                                            throw it
                                                        }
                                                    }
                                                    imageRef.downloadUrl
                                                }.addOnCompleteListener { task ->
                                                    if (task.isSuccessful) {
                                                        val imageUrl = task.result.toString()
                                                        Toast.makeText(context, "Image upload success", Toast.LENGTH_SHORT).show()

                                                        CoroutineScope(Dispatchers.Main).launch {
                                                            postItemViewModel.postItem(
                                                                name,
                                                                description,
                                                                location,
                                                                sublocation,
                                                                amount,
                                                                selectedCategory?.id ?: 0,
                                                                1,
                                                                image
                                                            ).let { response ->

                                                                Toast.makeText(
                                                                    context,
                                                                    response.message,
                                                                    Toast.LENGTH_LONG
                                                                ).show()

                                                                if (response.success) {
                                                                    navController.navigate("home")
                                                                    getListingsViewModel.fetchListings()
                                                                    getUserListingsViewModel.fetchListings()

                                                                } else {
                                                                    Toast.makeText(
                                                                        context,
                                                                        response.message,
                                                                        Toast.LENGTH_LONG
                                                                    ).show()
                                                                }

                                                            }
                                                        }

                                                    } else {
                                                        // Handle unsuccessful image upload
                                                        Toast.makeText(context, "Image upload failed", Toast.LENGTH_SHORT).show()
                                                    }
                                                }
                                                uploadTask.addOnFailureListener { exception ->
                                                    Log.e("FirebaseStorage", "Upload failed: ${exception.message}")
                                                    Toast.makeText(context, "Something went wrong, check connection and try again", Toast.LENGTH_LONG).show()
                                                }
                                            } else {
                                                Log.e("FirebaseStorage", "Failed to delete images inside folder: ${task.exception}")
                                            }
                                        }
                                    }.addOnFailureListener { exception ->
                                        Log.e("FirebaseStorage", "Failed to list items in folder: $exception")
                                    }
                                }else{
                                    Toast.makeText(context,"Please upload an image to continue", Toast.LENGTH_LONG).show()
                                }


                            } catch (e: Exception) {
                                Toast.makeText(context, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
                                Log.d("create listing","${e.message}")
                                // You can also log the exception for debugging purposes
                                e.printStackTrace()
                            }finally {
                                isPosting = false

                            }
                        }
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                if (isPosting) {
                    // Show loading spinner if posting is in progress
                    LoadingSpinner(isLoading = true)

                } else {
                    // Show "Create" text when not posting
                    Text(text = "Create")
                }
            }
        }

    }
}
@Composable
fun ItemImage(bitmap: Bitmap?, contentDescription: String?, modifier: Modifier) {
    Box(
        modifier = modifier
            .size(120.dp)
            .background(Color.LightGray)
    ) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = contentDescription,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}


