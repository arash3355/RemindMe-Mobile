package com.example.remindmeapp.firebase
import com.example.remindmeapp.model.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FirestoreManager {

    val db = FirebaseFirestore.getInstance()

    private val uid: String
        get() = FirebaseAuth.getInstance().currentUser!!.uid

    fun downloadTasks(

        onSuccess: (MutableList<Task>) -> Unit,

        onFailure: (String) -> Unit

    ) {

        db.collection("users")
            .document(uid)
            .collection("tasks")

            .get()

            .addOnSuccessListener { result ->

                val list = mutableListOf<Task>()

                result.documents.forEach { doc ->

                    val task = doc.toObject(Task::class.java)

                    if (task != null) {

                        list.add(task)

                    }

                }

                onSuccess(list)

            }

            .addOnFailureListener {

                onFailure(
                    it.localizedMessage ?: "Download failed."
                )

            }

    }

    fun uploadTasks(
        taskList: List<Task>,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        val batch = db.batch()

        val taskRef = db.collection("users")
            .document(uid)
            .collection("tasks")

        taskList.forEach { task ->

            val doc = taskRef.document()

            task.id = doc.id

            batch.set(doc, task)

        }

        batch.commit()

            .addOnSuccessListener {

                onSuccess()

            }

            .addOnFailureListener {

                onFailure(
                    it.localizedMessage ?: "Upload failed."
                )

            }

    }

    fun addTask(

        task: Task,

        onSuccess: () -> Unit,

        onFailure: (String) -> Unit

    ) {

        val doc =

            db.collection("users")
                .document(uid)
                .collection("tasks")
                .document()

        task.id = doc.id

        doc.set(task)

            .addOnSuccessListener {

                onSuccess()

            }

            .addOnFailureListener {

                onFailure(

                    it.localizedMessage ?: "Failed"

                )

            }

    }

    fun getTasks(

        onSuccess: (MutableList<Task>) -> Unit,

        onFailure: (String) -> Unit

    ) {

        db.collection("users")
            .document(uid)
            .collection("tasks")

            .get()

            .addOnSuccessListener { result ->

                val list = mutableListOf<Task>()

                result.documents.forEach {

                    val task =

                        it.toObject(Task::class.java)

                    if (task != null)

                        list.add(task)

                }

                onSuccess(list)

            }

            .addOnFailureListener {

                onFailure(

                    it.localizedMessage
                        ?: "Failed to load tasks"

                )

            }

    }

    fun updateTask(

        task: Task,

        onSuccess: () -> Unit,

        onFailure: (String) -> Unit

    ) {

        db.collection("users")
            .document(uid)
            .collection("tasks")
            .document(task.id)

            .set(task)

            .addOnSuccessListener {

                onSuccess()

            }

            .addOnFailureListener {

                onFailure(

                    it.localizedMessage ?: "Update failed"

                )

            }

    }

    fun deleteTask(

        taskId: String,

        onSuccess: () -> Unit,

        onFailure: (String) -> Unit

    ) {

        db.collection("users")
            .document(uid)
            .collection("tasks")
            .document(taskId)

            .delete()

            .addOnSuccessListener {

                onSuccess()

            }

            .addOnFailureListener {

                onFailure(

                    it.localizedMessage ?: "Delete failed"

                )

            }

    }



}