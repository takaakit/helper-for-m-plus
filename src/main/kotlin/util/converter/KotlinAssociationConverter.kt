package main.kotlin.util.converter
// ˅
import com.change_vision.jude.api.inf.editor.ModelEditorFactory
import com.change_vision.jude.api.inf.exception.InvalidEditingException
import com.change_vision.jude.api.inf.model.IAssociation
// ˄

// Convert the Kotlin association type of the target association.
class KotlinAssociationConverter(targetAssociation: IAssociation) {
    // ˅
    
    // ˄

    enum class CollectionKind(val value: String) {

        MUTABLE_LIST("MutableList"),

        MUTABLE_SET("MutableSet"),

        LIST("List"),

        SET("Set"),

        STACK("Stack"),

        ARRAY("Array"),

        ARRAY_LIST("ArrayList"),

        ARRAY_DEQUE("ArrayDeque"),

        LINKED_LIST("LinkedList"),

        HASH_SET("HashSet"),

        LINKED_HASH_SET("LinkedHashSet"),

        TREE_SET("TreeSet"),

        MUTABLE_MAP("MutableMap"),

        MAP("Map"),

        HASH_MAP("HashMap"),

        LINKED_HASH_MAP("LinkedHashMap"),

        TREE_MAP("TreeMap"),

        NO_COLLECTION("");

        // ˅
        
        // ˄
    }

    private var targetAssociation: IAssociation = targetAssociation
        // ˅
        
        // ˄

    // Check whether it is the same as the argument collection kind.
    fun isKotlinCollectionKind(kinds: List<CollectionKind>): Boolean {
        // ˅
        // If the argument kinds dose not exist, return false
        kinds.forEach {
            if (it.value.isNotEmpty()
                    && !targetAssociation.stereotypes.contains(it.value)) {
                return false
            }
        }

        // If kinds other than the argument kinds exist, return false
        CollectionKind.values().subtract(kinds).forEach {
            if (it.value.isNotEmpty()
                    && targetAssociation.stereotypes.contains(it.value)) {
                return false
            }
        }

        return true
        // ˄
    }

    // Convert to the argument collection kind.
    fun convertKotlinCollectionKind(kinds: List<CollectionKind>) {
        // ˅
        // Remove the related stereotypes
        for (stereotype in targetAssociation.stereotypes) {
            if (Regex("collection_kind *=.*").containsMatchIn(stereotype.toString().trim().lowercase())) {
                targetAssociation.removeStereotype(stereotype.toString())
            } else {
                for (collectionKind in CollectionKind.values()) {
                    if (stereotype == collectionKind.value) {
                        targetAssociation.removeStereotype(stereotype)
                    }
                }
            }
        }

        // Clear the related taggedValues
        for (taggedValue in targetAssociation.taggedValues) {
            if (taggedValue.key.trim().lowercase() == "collection_kind") {
                taggedValue.value = ""
            }
        }

        kinds.forEach {
            val basicModelEditor = ModelEditorFactory.getBasicModelEditor()
            try {
                // Create "collection_kind" tagged value
                basicModelEditor.createTaggedValue(targetAssociation, "collection_kind", it.value)
            } catch (e: InvalidEditingException) {
                // Set "collection_kind" tagged value
                for (taggedValue in targetAssociation.taggedValues) {
                    if (taggedValue.key.trim().lowercase() == "collection_kind") {
                        taggedValue.value = it.value
                    }
                }
            }

            // Add stereotype
            // Notice: This setting makes it easy to grasp. The setting does not affect code generation.
            if (it.value.isNotEmpty()
                    && !targetAssociation.hasStereotype(it.value)) {
                targetAssociation.addStereotype(it.value)
            }
        }
        // ˄
    }

    // ˅
    
    // ˄
}

// ˅

// ˄
