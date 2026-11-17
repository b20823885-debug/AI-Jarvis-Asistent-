package uz.jarvis.life

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TasksScreen(
    vm: MainViewModel,
    onEdit: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    var tab by rememberSaveable {
        mutableIntStateOf(0)
    }

    val active = vm.tasks
        .filter { !it.done }
        .sortedWith(
            compareBy<Task>(
                { it.dueMillis == null },
                { it.dueMillis ?: 0L }
            )
        )

    val done = vm.tasks
        .filter { it.done }
        .sortedByDescending { it.doneAt ?: 0L }

    val list = if (tab == 0) active else done

    Column(modifier.fillMaxSize()) {

        Text(
            "Vazifalar",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                start = 16.dp,
                top = 16.dp,
                end = 16.dp,
                bottom = 8.dp
            )
        )

        TabRow(
            selectedTabIndex = tab,
            containerColor = Color.Transparent
        ) {
            Tab(
                selected = tab == 0,
                onClick = { tab = 0 },
                text = { Text("Faol (${active.size})") }
            )

            Tab(
                selected = tab == 1,
                onClick = { tab = 1 },
                text = { Text("Bajarilgan (${done.size})") }
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                16.dp, 12.dp, 16.dp, 96.dp
            ),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (list.isEmpty()) {
                item {
                    if (tab == 0) {
                        EmptyState(
                            "🌿",
                            "Faol vazifa yo'q",
                            "＋ tugmasi bilan yangi vazifa qo'shing."
                        )
                    } else {
                        EmptyState(
                            "📭",
                            "Hali bajarilgan vazifa yo'q",
                            "Bajargan vazifalaringiz shu yerda ko'rinadi."
                        )
                    }
                }
            }

            items(list, key = { it.id }) { t ->
                TaskRow(
                    t,
                    onToggle = { vm.toggleDone(t) },
                    onClick = { onEdit(t) }
                )
            }
        }
    }
}
