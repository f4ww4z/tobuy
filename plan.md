# To Buy app

## DB structure

Purchase(name: String -> PrimaryKey)
- description : String
- price: Double
- quantity: Int
- totalPrice: Double (ignore)
- included: Boolean 

## Activities

#### Main ui

- Display a List<Purchase> in RecyclerView
- FAB for a new item
- On Purchase item's long click, show update dialog

## Layout

**recycler_item.xml**:

- TextViews
    - item name
    - item description
    - quantity
    - total price
- Checkboxes
    - enabled
