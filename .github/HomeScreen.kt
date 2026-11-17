if (active.isEmpty()) {
    item {
        EmptyState(
            "🎯",
            "Bugun uchun vazifa yo'q",
            "＋ tugmasi yoki ovozli buyruq bilan yangisini qo'shing."
        )
    }
} else {
    items(active, key = { it.id }) { t ->
        TaskRow(
            t,
            onToggle = { vm.toggleDone(t) },
            onClick = { onEditTask(t) }
        )
    }
}

if (vm.habits.isNotEmpty()) {
    item {
        SectionTitle("Odatlar")
    }

    items(vm.habits, key = { it.id }) { h ->
        HabitCard(
            h,
            onToggle = { vm.toggleHabitToday(h) },
            onClick = { onEditHabit(h) }
        )
    }
}
@Composable
private fun ProgressCard(
    progress: Float,
    finished: Int,
    total: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.size(76.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 8.dp,
                    trackColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                        alpha = 0.15f
                    )
                )

                Text(
                    "${(progress * 100).toInt()}%",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(Modifier.width(18.dp))

            Column {
                Text(
                    "Bugungi progress",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(Modifier.height(2.dp))

                Text(
                    if (total == 0) "Bugun uchun reja yo'q"
                    else "$finished / $total bajarildi",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}
