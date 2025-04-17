import { Badge } from "@/components/ui/badge"
import { Button } from "@/components/ui/button"
import { ScrollArea } from "@/components/ui/scroll-area"
import { Separator } from "@/components/ui/separator"
import {
  Command,
  CommandInput,
  CommandItem,
  CommandList,
} from "cmdk"
import { Minus, Plus, X } from "lucide-react"
import { useEffect, useState } from "react"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"

type SelectedItem = {
  name: string
  quantity: number
}

export type SelectedPerson = {
  name: string
}

interface TagSelectProps {
  title?: string
  placeholder?: string
  data?: string[]
  variant?: "default" | "people"
  onChange?: (items: SelectedItem[]) => void

}

const TagSelect: React.FC<TagSelectProps> = ({
  title = "Items",
  placeholder = "Search items...",
  data = [],
  variant = "default",
  onChange
}) => {
  const [query, setQuery] = useState("")
  const [options, setOptions] = useState<string[]>([])
  const [selectedItems, setSelectedItems] = useState<SelectedItem[]>([])

  useEffect(() => {
    const timeout = setTimeout(() => {
      const filtered = data.filter((item) =>
        item.toLowerCase().includes(query.toLowerCase())
      )
      setOptions(filtered.slice(0, 5))
    }, 300)
    return () => clearTimeout(timeout)
  }, [query, data])

  useEffect(() => {
    onChange?.(selectedItems)
  }, [selectedItems, onChange])
  

  const handleSelect = (item: string) => {
    const exists = selectedItems.find((i) => i.name === item)
    if (!exists) {
      setSelectedItems([...selectedItems, { name: item, quantity: 1 }])
    }
    setQuery("")
  }

  const handleRemove = (item: string) => {
    setSelectedItems(selectedItems.filter((i) => i.name !== item))
  }

  const updateQuantity = (name: string, delta: number) => {
    setSelectedItems((prev) =>
      prev.map((i) =>
        i.name === name
          ? { ...i, quantity: Math.max(1, i.quantity + delta) }
          : i
      )
    )
  }

  return (
    <Card className="w-full max-w-md mx-auto shadow-md rounded-lg mt-4">
      <CardHeader>
        <CardTitle className="text-base">{title}</CardTitle>
      </CardHeader>
      <CardContent className="space-y-4">
        <Command>
          <div className="border rounded-md px-3 py-2 bg-transparent focus-within:ring-2 focus-within:ring-blue-500 transition-all">
            <CommandInput
              value={query}
              onValueChange={setQuery}
              placeholder={placeholder}
              className="bg-transparent placeholder:text-muted-foreground text-sm w-full outline-none"
            />
          </div>
          {query && (
            <ScrollArea className="max-h-40 border rounded-md mt-2 bg-white shadow-lg z-10">
              <CommandList>
                {options.length > 0 ? (
                  options.map((item, index) => (
                    <CommandItem
                      key={index}
                      onSelect={() => handleSelect(item)}
                      className="cursor-pointer hover:bg-blue-500 hover:text-white p-2 transition-all"
                    >
                      {item}
                    </CommandItem>
                  ))
                ) : (
                  <div className="p-2 text-sm text-muted-foreground">
                    No results found
                  </div>
                )}
              </CommandList>
            </ScrollArea>
          )}
        </Command>

        {selectedItems.length > 0 && (
          <>
            <Separator />
            <div className="flex flex-wrap gap-2">
              {selectedItems.map((item, idx) => (
                <Badge
                  key={idx}
                  variant="outline"
                  className="flex items-center gap-2 px-2 py-1"
                >
                  {item.name}
                  {variant === "default" && (
                    <>
                      <span>({item.quantity})</span>
                      <Button
                        variant="ghost"
                        size="icon"
                        className="w-4 h-4 p-0 bg-transparent"
                        onClick={() => updateQuantity(item.name, -1)}
                      >
                        <Minus className="w-3 h-3" />
                      </Button>
                      <Button
                        variant="ghost"
                        size="icon"
                        className="w-4 h-4 p-0 bg-transparent"
                        onClick={() => updateQuantity(item.name, 1)}
                      >
                        <Plus className="w-3 h-3" />
                      </Button>
                    </>
                  )}
                  <Button
                    variant="ghost"
                    size="icon"
                    className="w-4 h-4 p-0 ml-1 bg-transparent"
                    onClick={() => handleRemove(item.name)}
                  >
                    <X className="w-3 h-3" />
                  </Button>
                </Badge>
              ))}
            </div>
          </>
        )}
      </CardContent>
    </Card>
  )
}

export default TagSelect
