package main.kotlin.util.collector
// ˅
import com.change_vision.jude.api.inf.AstahAPI
import com.change_vision.jude.api.inf.model.INamedElement
import com.change_vision.jude.api.inf.model.IPackage
import com.change_vision.jude.api.inf.model.IAssociation
import com.change_vision.jude.api.inf.model.IClass
import com.change_vision.jude.api.inf.model.IAttribute
import java.util.LinkedHashSet
// ˄

// Collect the selected associations in the structure tree recursively.
class AssociationCollector {
    // ˅
    
    // ˄

    // Collect the selected associations in the structure tree recursively.
    fun collectFromSelectedElements(): List<IAssociation> {
        // ˅
        val selectedEntities = java.util.Arrays.asList(*AstahAPI.getAstahAPI().viewManager.projectViewManager.selectedEntities)
        println("Selected entities :".plus(selectedEntities.toString()))

        val associationSet = LinkedHashSet<IAssociation>()  // Notice: Not allow duplication

        // Collect associations from the selected entities recursively
        selectedEntities.forEach {
            if (it is INamedElement) {
                associationSet.addAll(collectRecursively(it))
            }
        }
        println("Collected associations :".plus(associationSet.toString()))

        return ArrayList<IAssociation>(associationSet)
        // ˄
    }

    // Collect associations recursively.
    private fun collectRecursively(element: INamedElement): List<IAssociation> {
        // ˅
        val associationList = ArrayList<IAssociation>()

        if (element is IPackage) {
            // Add all associations from the package
            element.ownedElements.forEach {
                associationList.addAll(collectRecursively(it))
            }

        } else if (element is IClass) {
            // Add all associations from the class
            element.attributes.forEach {
                associationList.addAll(collectRecursively(it))
            }

            // Add all associations from the nested classes
            element.nestedClasses.forEach {
                associationList.addAll(collectRecursively(it))
            }

        } else if (element is IAttribute) {
            val asso = element.association
            if (asso == null) {
                // If attribute, do nothing
            } else {
                // If association, add association
                for (memberEnd in asso.memberEnds) {
                    if (memberEnd === element && memberEnd.navigability == "Navigable") {
                        // If valid association end
                        associationList.add(asso)
                    }
                }
            }
        }

        return ArrayList<IAssociation>(LinkedHashSet<IAssociation>(associationList))  // Delete duplicates
        // ˄
    }

    // ˅
    
    // ˄
}

// ˅

// ˄
