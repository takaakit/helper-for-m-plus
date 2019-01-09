package main.kotlin.service
// ˅
import com.change_vision.jude.api.inf.AstahAPI
import com.change_vision.jude.api.inf.editor.TransactionManager
import com.change_vision.jude.api.inf.model.IElement
import com.change_vision.jude.api.inf.model.INamedElement
import com.change_vision.jude.api.inf.ui.IPluginActionDelegate
import com.change_vision.jude.api.inf.ui.IWindow
import javax.swing.JOptionPane
// ˄

// This class is realized by the classes of service package.
interface IMyPluginActionDelegate : IPluginActionDelegate {

    // Collect the selected model elements
    fun collectSelectedElements(): List<INamedElement>

    // Exclude already set model elements
    fun excludeSetElements(selectedElements: List<INamedElement>): List<INamedElement>

    // Edit model elements
    fun editElements(elements: List<INamedElement>)

    // ˅
    // This function has an implementation.
    // M PLUS plug-in dose not generate a function with implementation in the interface,
    // so write this function in the user code area.
    override fun run(window: IWindow) {
        try {
            // Exclude already set model elements out of the selected model elements.
            val editTargets = excludeSetElements(collectSelectedElements())

            // If no elements to be edited, the processing is terminated.
            if (editTargets.isEmpty()) {
                JOptionPane.showMessageDialog(window.parent, "No elements ware edited.", "[M+] Information", JOptionPane.INFORMATION_MESSAGE)
                return
            }

            val firstSelectedElement: IElement = AstahAPI.getAstahAPI().viewManager.projectViewManager.selectedEntities[0] as IElement

            // Between begin and end transaction is one update transaction.
            TransactionManager.beginTransaction()
            editElements(editTargets)               // Edit model elements.
            TransactionManager.endTransaction()

            // Select the first selected model element in the structure tree,
            // because element selection is canceled after editing.
            AstahAPI.getAstahAPI().viewManager.projectViewManager.showInStructureTree(firstSelectedElement)
        } catch (e: Exception) {
            TransactionManager.abortTransaction()

            val errorMessage = StringBuilder(e.toString())
            for (stLine in e.stackTrace) {
                errorMessage.append(System.getProperty("line.separator")).append(stLine)
            }
            JOptionPane.showMessageDialog(window.parent, errorMessage, "[M+] Error", JOptionPane.ERROR_MESSAGE)
        }
    }
    // ˄
}

// ˅

// ˄
