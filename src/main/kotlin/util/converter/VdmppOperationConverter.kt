package main.kotlin.util.converter
// ˅
import com.change_vision.jude.api.inf.model.IOperation
// ˄

// Convert the VDM++ operation type of the target operation.
class VdmppOperationConverter(targetOperation: IOperation) {
    // ˅
    
    // ˄

    enum class OperationKind(val value: String) {

        OPERATION(""),

        FUNCTION("function"),

        TOTAL_FUNCTION("total_function");

        // ˅
        
        // ˄
    }

    private var targetOperation: IOperation = targetOperation
        // ˅
        
        // ˄

    // Check whether it is the same as the argument operation kind.
    fun isVdmppOperationKind(kinds: List<OperationKind>): Boolean {
        // ˅
        // If the argument kinds dose not exist, return false
        kinds.forEach {
            if (it.value.isNotEmpty()
                    && !targetOperation.stereotypes.contains(it.value)) {
                return false
            }
        }

        // If kinds other than the argument kinds exist, return false
        OperationKind.values().subtract(kinds).forEach {
            if (it.value.isNotEmpty()
                    && targetOperation.stereotypes.contains(it.value)) {
                return false
            }
        }

        return true
        // ˄
    }

    // Convert to the argument operation kind.
    fun convertVdmppOperationKind(kinds: List<OperationKind>) {
        // ˅
        // Remove the related stereotypes
        OperationKind.values().forEach {
            if (it.value.isNotEmpty()
                    && targetOperation.hasStereotype(it.value)) {
                targetOperation.removeStereotype(it.value)
            }
        }

        // Set stereotypes
        kinds.forEach {
            if (it.value.isNotEmpty()
                    && !targetOperation.hasStereotype(it.value)) {
                targetOperation.addStereotype(it.value)
            }
        }
        // ˄
    }

    // ˅
    
    // ˄
}

// ˅

// ˄
