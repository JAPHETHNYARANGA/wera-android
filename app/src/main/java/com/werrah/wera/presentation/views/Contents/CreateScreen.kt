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
fun FavoritesScreen(
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
                            "Kajiado", "Kiambu", "Mombasa", "Nairobi", "Nakuru", "Baringo", "Bomet", "Bungoma", "Busia", "Elgeyo-Marakwet", "Embu",
                            "Garisa", "Homa Bay", "Isiolo", "Kakamega", "Kericho", "Kilifi", "Kirinyaga", "Kisii", "Kisumu", "Kitui", "Kwale", "Laikipia", "Lamu", "Machakos", "Makueni", "Mandera", "Marsabit", "Meru",
                            "Migori", "Muranga", "Nandi", "Narok", "Nyamira", "Nyandarua", "Nyeri", "Samburu", "Siaya", "Taita Taveta", "Tana River", "Tharaka-Nithi", "Trans-Nzoia", "Turkana", "Uasin Gishu", "Vihiga", "Wajir", "West Pokot"
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
                                val Bomet = listOf("All Bomet",)
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
                                }else if (amount.isEmpty()){
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


