package main.kotlin.util.collector
// ˅
import com.change_vision.jude.api.inf.AstahAPI
import com.change_vision.jude.api.inf.model.IAttribute
import com.change_vision.jude.api.inf.model.IClass
import com.change_vision.jude.api.inf.model.INamedElement
import com.change_vision.jude.api.inf.model.IPackage
import java.util.LinkedHashSet
// ˄

// Collect the selected attributes in the structure tree recursively.
class AttributeCollector {
    // ˅
    
    // ˄

    // Collect the selected attributes in the structure tree recursively.
    fun collectFromSelectedElements(): List<IAttribute> {
        // ˅
        val selectedEntities = java.util.Arrays.asList(*AstahAPI.getAstahAPI().viewManager.projectViewManager.selectedEntities)
        println("Selected entities :".plus(selectedEntities.toString()))

        val attributeSet = LinkedHashSet<IAttribute>()  // Notice: Not allow duplication

        // Collect attributes from the selected entities recursively
        selectedEntities.forEach {
            if (it is INamedElement) {
                attributeSet.addAll(collectRecursively(it))
            }
        }
        println("Collected attributes :".plus(attributeSet.toString()))

        return ArrayList<IAttribute>(attributeSet)
        // ˄
    }

    // Collect attributes recursively.
    private fun collectRecursively(element: INamedElement): List<IAttribute> {
        // ˅
        val attributeList = ArrayList<IAttribute>()

        if (element is IPackage) {
            // Add all attributes from the package
            element.ownedElements.forEach {
                attributeList.addAll(collectRecursively(it))
            }

        } else if (element is IClass) {
            // Add all attributes from the class
            element.attributes.forEach {
                attributeList.addAll(collectRecursively(it))
            }

            // Add all attributes from the nested classes
            element.nestedClasses.forEach {
                attributeList.addAll(collectRecursively(it))
            }

        } else if (element is IAttribute) {
            if (element.association == null) {
                // If not association, add attribute
                attributeList.add(element)
            } else {
                // If association, do nothing
            }
        }

        return ArrayList<IAttribute>(LinkedHashSet<IAttribute>(attributeList))  // Delete duplicates
        // ˄
    }

    // ˅
    
    // ˄
}

// ˅

// ˄
