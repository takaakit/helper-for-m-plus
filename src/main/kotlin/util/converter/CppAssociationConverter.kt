package main.kotlin.util.converter
// ˅
import com.change_vision.jude.api.inf.editor.ModelEditorFactory
import com.change_vision.jude.api.inf.exception.InvalidEditingException
import com.change_vision.jude.api.inf.model.IAssociation
// ˄

// Convert the C++ association type of the target association.
class CppAssociationConverter(targetAssociation: IAssociation) {
    // ˅
    
    // ˄

    enum class CollectionKind(val value: String) {

        VECTOR("vector"),

        LIST("list"),

        DEQUE("deque"),

        STACK("stack"),

        QUEUE("queue"),

        PRIORITY_QUEUE("priority_queue"),

        SET("set"),

        MULTISET("multiset"),

        MAP("map"),

        MULTIMAP("multimap"),

        NO_COLLECTION("");

        // ˅
        
        // ˄
    }

    enum class PointerKind(val value: String) {

        UNIQUE_PTR("unique_ptr"),

        SHARED_PTR("shared_ptr"),

        WEAK_PTR("weak_ptr"),

        RAW_POINTER("");

        // ˅
        
        // ˄
    }

    private var targetAssociation: IAssociation = targetAssociation
        // ˅
        
        // ˄

    // Check whether it is the same as the argument collection kind.
    fun isCppCollectionKind(kinds: List<CollectionKind>): Boolean {
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

    // Check whether it is the same as the argument pointer kind.
    fun isCppPointerKind(kinds: List<PointerKind>): Boolean {
        // ˅
        // If the argument kinds dose not exist, return false
        kinds.forEach {
            if (it.value.isNotEmpty()
                    && !targetAssociation.stereotypes.contains(it.value)) {
                return false
            }
        }

        // If kinds other than the argument kinds exist, return false
        PointerKind.values().subtract(kinds).forEach {
            if (it.value.isNotEmpty()
                    && targetAssociation.stereotypes.contains(it.value)) {
                return false
            }
        }

        return true
        // ˄
    }

    // Convert to the argument collection kind.
    fun convertCppCollectionKind(kinds: List<CollectionKind>) {
        // ˅
        // Remove the related stereotypes
        for (stereotype in targetAssociation.stereotypes) {
            if (Regex("collection_kind *=.*").containsMatchIn(stereotype.toString().trim().toLowerCase())) {
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
            if (taggedValue.key.trim().toLowerCase() == "collection_kind") {
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
                    if (taggedValue.key.trim().toLowerCase() == "collection_kind") {
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

    // Convert to the argument pointer kind.
    fun convertCppPointerKind(kinds: List<PointerKind>) {
        // ˅
        // Remove the related stereotypes
        for (stereotype in targetAssociation.stereotypes) {
            if (Regex("pointer_kind *=.*").containsMatchIn(stereotype.toString().trim().toLowerCase())) {
                targetAssociation.removeStereotype(stereotype.toString())
            } else {
                for (pointerKindCpp in PointerKind.values()) {
                    if (stereotype == pointerKindCpp.value) {
                        targetAssociation.removeStereotype(stereotype)
                    }
                }
            }
        }

        // Clear the related taggedValues
        for (taggedValue in targetAssociation.taggedValues) {
            if (taggedValue.key.trim().toLowerCase() == "pointer_kind") {
                taggedValue.value = ""
            }
        }

        kinds.forEach {
            val basicModelEditor = ModelEditorFactory.getBasicModelEditor()
            try {
                // Create "pointer_kind" tagged value
                basicModelEditor.createTaggedValue(targetAssociation, "pointer_kind", it.value)
            } catch (e: InvalidEditingException) {
                // Set "pointer_kind" tagged value
                for (taggedValue in targetAssociation.taggedValues) {
                    if (taggedValue.key.trim().toLowerCase() == "pointer_kind") {
                        taggedValue.value = it.value
                    }
                }
            }

            // Add stereotype
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
