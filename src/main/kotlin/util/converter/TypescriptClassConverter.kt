package main.kotlin.util.converter
// ˅
import com.change_vision.jude.api.inf.model.IClass
import java.util.*
import java.text.*

// ˄

// Convert the TypeScript class type of the target class.
class TypescriptClassConverter(targetClass: IClass) {
    // ˅
    
    // ˄

    enum class ClassKind(val value: String) {

        CLASS(""),

        ENUM("enum");

        // ˅
        
        // ˄
    }

    private var targetClass: IClass = targetClass
        // ˅
        
        // ˄

    // Check whether it is the same as the argument class kind.
    fun isTypescriptClassKind(kinds: List<ClassKind>): Boolean {
        // ˅
        // If the argument kinds dose not exist, return false
        kinds.forEach {
            if (it.value.isNotEmpty()
                    && !targetClass.stereotypes.contains(it.value)) {
                return false
            }
        }

        // If kinds other than the argument kinds exist, return false
        ClassKind.values().subtract(kinds).forEach {
            if (it.value.isNotEmpty()
                    && targetClass.stereotypes.contains(it.value)) {
                return false
            }
        }

        return true
        // ˄
    }

    // Convert to the argument class kind.
    fun convertTypescriptClassKind(kinds: List<ClassKind>) {
        // ˅
        // Remove the related stereotypes
        ClassKind.values().forEach {
            if (it.value.isNotEmpty()
                    && targetClass.hasStereotype(it.value)) {
                targetClass.removeStereotype(it.value)
            }
        }

        // Set stereotypes
        kinds.forEach {
            if (it.value.isNotEmpty()
                    && !targetClass.hasStereotype(it.value)) {
                targetClass.addStereotype(it.value)
            }
        }
        // ˄
    }

    // ˅
    
    // ˄
}

// ˅

// ˄
