package uz.jarvis.life

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HabitsScreen(
    vm: MainViewModel,
    onEdit: (Habit) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxSize()) {

        Text(
            "Odatlar",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                start = 16.dp,
                top = 16.dp,
                end = 16.dp,
                bottom = 8.dp
            )
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                16.dp, 4.dp, 16.dp, 96.dp
            ),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (vm.habits.isEmpty()) {
                item {
                    EmptyState(
                        "🔥",
                        "Birinchi odatingizni qo'shing",
                        "Har kuni belgilab borsangiz, ketma-ket kunlar hisobi o'sadi."
                    )
                }
            }

            items(vm.habits, key = { it.id }) { h ->
                HabitCard(
                    h,
                    onToggle = { vm.toggleHabitToday(h) },
                    onClick = { onEdit(h) }
                )
            }
        }
    }
}
