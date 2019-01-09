package main.kotlin.util.collector
// ˅
import com.change_vision.jude.api.inf.AstahAPI
import com.change_vision.jude.api.inf.model.IClass
import com.change_vision.jude.api.inf.model.INamedElement
import com.change_vision.jude.api.inf.model.IPackage
import java.util.LinkedHashSet
// ˄

// Collect the selected classes in the structure tree recursively.
class ClassCollector {
    // ˅
    
    // ˄

    // Collect the selected classes in the structure tree recursively.
    fun collectFromSelectedElements(): List<IClass> {
        // ˅
        val selectedEntities = java.util.Arrays.asList(*AstahAPI.getAstahAPI().viewManager.projectViewManager.selectedEntities)
        println("Selected entities :".plus(selectedEntities.toString()))

        val classSet = LinkedHashSet<IClass>()  // Notice: Not allow duplication

        // Collect classes from the selected entities recursively
        selectedEntities.forEach {
            if (it is INamedElement) {
                classSet.addAll(collectRecursively(it))
            }
        }
        println("Collected classes :".plus(classSet.toString()))

        return ArrayList<IClass>(classSet)
        // ˄
    }

    // Collect classes recursively.
    private fun collectRecursively(element: INamedElement): List<IClass> {
        // ˅
        val classList = ArrayList<IClass>()

        if (element is IPackage) {
            // Add all attributes from the package
            element.ownedElements.forEach {
                classList.addAll(collectRecursively(it))
            }
        } else if (element is IClass) {
            // Add class
            classList.add(element)

            // Add all classes from the nested classes
            element.nestedClasses.forEach {
                classList.addAll(collectRecursively(it))
            }
        }

        return ArrayList<IClass>(LinkedHashSet<IClass>(classList))  // Delete duplicates
        // ˄
    }

    // ˅
    
    // ˄
}

// ˅

// ˄
