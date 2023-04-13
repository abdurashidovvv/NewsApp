package uz.ilhomjon.newsapp.models.categoryFragment

class CategoryItem {

    var name:String?=null
    var image:Int?=null

    constructor(name: String?, image: Int?) {
        this.name = name
        this.image = image
    }

    constructor()

    override fun toString(): String {
        return "CategoryItem(name=$name, image=$image)"
    }


}