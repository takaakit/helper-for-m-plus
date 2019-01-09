package main.kotlin.util.collector
// ˅
import com.change_vision.jude.api.inf.AstahAPI
import com.change_vision.jude.api.inf.model.IOperation
import com.change_vision.jude.api.inf.model.IClass
import com.change_vision.jude.api.inf.model.INamedElement
import com.change_vision.jude.api.inf.model.IPackage
import java.util.LinkedHashSet
// ˄

// Collect the selected operations in the structure tree recursively.
class OperationCollector {
    // ˅
    
    // ˄

    // Collect the selected operations in the structure tree recursively.
    fun collectFromSelectedElements(): List<IOperation> {
        // ˅
        val selectedEntities = java.util.Arrays.asList(*AstahAPI.getAstahAPI().viewManager.projectViewManager.selectedEntities)
        println("Selected entities :".plus(selectedEntities.toString()))

        val operationSet = LinkedHashSet<IOperation>()  // Notice: Not allow duplication

        // Collect operations from the selected entities recursively
        selectedEntities.forEach {
            if (it is INamedElement) {
                operationSet.addAll(collectRecursively(it))
            }
        }
        println("Collected operations :".plus(operationSet.toString()))

        return ArrayList<IOperation>(operationSet)
        // ˄
    }

    // Collect operations recursively.
    private fun collectRecursively(element: INamedElement): List<IOperation> {
        // ˅
        val operationList = ArrayList<IOperation>()

        if (element is IPackage) {
            // Add all operations from the package
            element.ownedElements.forEach {
                operationList.addAll(collectRecursively(it))
            }

        } else if (element is IClass) {
            // Add all operations from the class
            element.operations.forEach {
                operationList.addAll(collectRecursively(it))
            }

            // Add all operations from the nested classes
            element.nestedClasses.forEach {
                operationList.addAll(collectRecursively(it))
            }

        } else if (element is IOperation) {
            // Add operation
            operationList.add(element)
        }

        return ArrayList<IOperation>(LinkedHashSet<IOperation>(operationList))  // Delete duplicates
        // ˄
    }

    // ˅
    
    // ˄
}

// ˅

// ˄
