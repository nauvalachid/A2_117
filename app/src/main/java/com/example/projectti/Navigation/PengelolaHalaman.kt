package com.example.projectti.Navigation

import DestinasiUpdatePenulis
import UpdateViewPenulis
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projectti.ui.view.Buku.DestinasiEntryBuku
import com.example.projectti.ui.view.Buku.EntryBkScreen
import com.example.projectti.ui.view.HalamanAwal.DestinasiHalamanUtama
import com.example.projectti.ui.view.HalamanAwal.HomeHalamanAwalScreen
import com.example.projectti.ui.view.Kategori.DestinasiDetailKategori
import com.example.projectti.ui.view.Kategori.DestinasiEntryKategori
import com.example.projectti.ui.view.Kategori.DestinasiHomeKategori
import com.example.projectti.ui.view.Kategori.DestinasiUpdateKategori
import com.example.projectti.ui.view.Kategori.DetailViewKategori
import com.example.projectti.ui.view.Kategori.EntryKtgrScreen
import com.example.projectti.ui.view.Kategori.HomeScreenKategori
import com.example.projectti.ui.view.Kategori.UpdateViewKategori
import com.example.projectti.ui.view.Penerbit.DestinasiDetailPenerbit
import com.example.projectti.ui.view.Penerbit.DestinasiEntryPenerbit
import com.example.projectti.ui.view.Penerbit.DestinasiHomePenerbit
import com.example.projectti.ui.view.Penerbit.DestinasiUpdatePenerbit
import com.example.projectti.ui.view.Penerbit.DetailViewPenerbit
import com.example.projectti.ui.view.Penerbit.EntryPnrbtScreen
import com.example.projectti.ui.view.Penerbit.HomeScreenPenerbit
import com.example.projectti.ui.view.Penerbit.UpdateViewPenerbit
import com.example.projectti.ui.view.Penulis.DestinasiDetailPenulis
import com.example.projectti.ui.view.Penulis.DestinasiEntryPenulis
import com.example.projectti.ui.view.Penulis.DestinasiHomePenulis
import com.example.projectti.ui.view.Penulis.DetailViewPenulis
import com.example.projectti.ui.view.Penulis.EntryPnlsScreen
import com.example.projectti.ui.view.Penulis.HomeScreenPenulis
import com.example.projectti.ui.viewmodel.HalamanAwal.HomeHalamanAwalUiState

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHalamanUtama.route,
    ) {
        composable(DestinasiHalamanUtama.route) {
            HomeHalamanAwalScreen(
                onClickKategori = {
                    navController.navigate(DestinasiHomeKategori.route)
                },
                onClickPenerbit = {
                    navController.navigate(DestinasiHomePenerbit.route)
                },
                onClickPenulis = {
                    navController.navigate(DestinasiHomePenulis.route)
                },
                onDetailClick = {
                },
                navigateToItemEntry = {
                    navController.navigate(DestinasiEntryBuku.route)
                },
                modifier = Modifier
            )
        }

        //BUku
        composable(DestinasiEntryBuku.route) {
            EntryBkScreen(
                navigateBack = {
                    navController.popBackStack()
                },
            )
        }


        //Penulis
        composable(DestinasiHomePenulis.route) {
            HomeScreenPenulis(
                navigateToItemEntryPenulis = {
                    navController.navigate(DestinasiEntryPenulis.route)
                },
                onDetailClick = { idPenulis ->
                    if (idPenulis != null) {
                        navController.navigate("${DestinasiDetailPenulis.route}/$idPenulis")
                    }
                }
            )
        }

        composable(DestinasiEntryPenulis.route) {
            EntryPnlsScreen(
                navigateBack = {
                    navController.popBackStack()
                },
            )
        }

        composable(
            route = "${DestinasiDetailPenulis.route}/{idPenulis}",
            arguments = listOf(navArgument("idPenulis") { type = NavType.IntType })
        ) { backStackEntry ->
            // Ambil idPenulis sebagai Int atau null jika tidak ditemukan
            val idPenulis = backStackEntry.arguments?.getInt("idPenulis")
            idPenulis?.let {
                DetailViewPenulis(
                    idPenulis = it,
                    navigateBack = {
                        navController.navigate(DestinasiHomePenulis.route) {
                            popUpTo(DestinasiHomePenulis.route) {
                                inclusive = true
                            }
                        }
                    },
                    onDetailPenulisClick = {
                        // Navigasi ke layar update dengan idPenulis
                        navController.navigate("${DestinasiUpdatePenulis.route}/$it")
                    }
                )
            }
        }

        composable(
            route = "${DestinasiUpdatePenulis.route}/{idPenulis}",
            arguments = listOf(navArgument("idPenulis") { type = NavType.IntType })
        ) { backStackEntry ->
            // Ambil idPenulis sebagai Int atau null jika tidak ditemukan
            val idPenulis = backStackEntry.arguments?.getInt("idPenulis")
            idPenulis?.let {
                UpdateViewPenulis(
                    idPenulis = it,
                    navigateBack = {
                        navController.navigate(DestinasiHomePenulis.route) {
                            popUpTo(DestinasiHomePenulis.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }


        //Kategori
        composable(DestinasiHomeKategori.route) {
            HomeScreenKategori(
                navigateToItemEntryKategori = {
                    navController.navigate(DestinasiEntryKategori.route)
                },
                onDetailClick = { idKategori ->
                    idKategori?.let {
                        navController.navigate("${DestinasiDetailKategori.route}/$it")
                    }
                }
            )
        }

        composable(DestinasiEntryKategori.route) {
            EntryKtgrScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeKategori.route) {
                        popUpTo(DestinasiHomeKategori.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = "${DestinasiDetailKategori.route}/{idKategori}",
            arguments = listOf(navArgument("idKategori") { type = NavType.IntType })
        ) { backStackEntry ->
            // Ambil idPenulis sebagai Int atau null jika tidak ditemukan
            val idKategori = backStackEntry.arguments?.getInt("idKategori")
            idKategori?.let {
                DetailViewKategori(
                    idKategori = it,
                    navigateBack = {
                        navController.navigate(DestinasiHomeKategori.route) {
                            popUpTo(DestinasiHomeKategori.route) {
                                inclusive = true
                            }
                        }
                    },
                    onClick = {
                        // Navigasi ke layar update dengan idPenulis
                        navController.navigate("${DestinasiUpdateKategori.route}/$it")
                    }
                )
            }
        }

        composable(
            route = "${DestinasiUpdateKategori.route}/{idKategori}",
            arguments = listOf(navArgument("idKategori") { type = NavType.IntType })
        ) { backStackEntry ->
            // Ambil idKategori sebagai Int atau null jika tidak ditemukan
            val idKategori = backStackEntry.arguments?.getInt("idKategori")
            idKategori?.let {
                UpdateViewKategori(
                    idKategori = it,
                    navigateBack = {
                        navController.navigate(DestinasiHomeKategori.route) {
                            popUpTo(DestinasiHomeKategori.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }







        //Penerbit
        composable(DestinasiHomePenerbit.route) {
            HomeScreenPenerbit(
                navigateToItemEntryPenerbit = {
                    navController.navigate(DestinasiEntryPenerbit.route)
                },
                onDetailClick = { idPenerbit ->
                    if (idPenerbit != null) {
                        navController.navigate("${DestinasiDetailPenerbit.route}/$idPenerbit")
                    }
                }
            )
        }

        composable(DestinasiEntryPenerbit.route) {
            EntryPnrbtScreen(
                navigateBack = {
                    navController.popBackStack()
                },
            )
        }
    }
}
