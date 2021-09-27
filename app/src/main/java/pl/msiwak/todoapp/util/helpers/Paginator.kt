package pl.msiwak.todoapp.util.helpers

class Paginator {

    companion object {
        const val TOTAL_NUM_ITEMS = 52
        const val ITEMS_PER_PAGE = 7
        const val ITEMS_REMAINING = TOTAL_NUM_ITEMS % ITEMS_PER_PAGE
        const val LAST_PAGE = TOTAL_NUM_ITEMS / ITEMS_PER_PAGE
    }

    fun generatePage(currentPage: Int): ArrayList<String> {
        val startItem = currentPage * ITEMS_PER_PAGE + 1
        val numOfData = ITEMS_PER_PAGE
        val pageData: ArrayList<String> = ArrayList()
        if (currentPage == LAST_PAGE && ITEMS_REMAINING > 0) {
            for (i in startItem until startItem + ITEMS_REMAINING) {
                pageData.add("Number $i")
            }
        } else {
            for (i in startItem until startItem + numOfData) {
                pageData.add("Number $i")
            }
        }
        return pageData
    }

}