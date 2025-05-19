import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { ScrollArea } from "@/components/ui/scroll-area";
import { Separator } from "@/components/ui/separator";
import { Command, CommandInput, CommandItem, CommandList } from "cmdk";
import { Minus, Plus, X } from "lucide-react";
import { useEffect, useState } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";

// Kiểu dữ liệu cho các item tìm kiếm ra lựa chọn
export type OptionItem = {
  id: string;
  name: string;
};

// Kiểu dữ liệu cho các item đã được chọn
export type SelectedItem = {
  id: string;
  name: string;
  quantity: number;
};

interface TagSelectProps {
  title?: React.ReactNode;
  placeholder?: string;
  data?: OptionItem[];
  variant?: "default" | "people";
  onChange?: (items: SelectedItem[]) => void;
  onSearch?: (query: string) => Promise<OptionItem[]>;
}

const TagSelect: React.FC<TagSelectProps> = ({
  title = "Items",
  placeholder = "Search items...",
  data = [],
  variant = "default",
  onChange,
  onSearch,
}) => {
  const [query, setQuery] = useState("");
  const [options, setOptions] = useState<OptionItem[]>([]);
  const [selectedItems, setSelectedItems] = useState<SelectedItem[]>([]);

  useEffect(() => {
    if (!onSearch) {
      // Nếu không có onSearch, fallback filter data cũ
      setOptions(
        data
          .filter((item) =>
            item.name.toLowerCase().includes(query.toLowerCase())
          )
          .slice(0, 10)
      );
      return;
    }

    // Nếu có onSearch thì gọi API
    const timeout = setTimeout(() => {
      if (query.trim().length === 0) {
        setOptions([]);
        return;
      }
      onSearch(query)
        .then((results) => {
          setOptions(results.slice(0, 5));
        })
        .catch(() => {
          setOptions([]);
        });
    }, 300);

    return () => clearTimeout(timeout);
  }, [query, onSearch, data]);

  useEffect(() => {
    onChange?.(selectedItems);
  }, [selectedItems, onChange]);

  // Khi chọn 1 option, thêm vào selectedItems nếu chưa có
  const handleSelect = (item: OptionItem) => {
    const exists = selectedItems.find((i) => i.id === item.id);
    if (!exists) {
      setSelectedItems([
        ...selectedItems,
        { id: item.id, name: item.name, quantity: 1 },
      ]);
    }
    setQuery("");
    setOptions([]);
  };

  const handleRemove = (id: string) => {
    setSelectedItems(selectedItems.filter((i) => i.id !== id));
  };

  const updateQuantity = (id: string, delta: number) => {
    setSelectedItems((prev) =>
      prev.map((i) =>
        i.id === id ? { ...i, quantity: Math.max(1, i.quantity + delta) } : i
      )
    );
  };

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
                  options.map((item) => (
                    <CommandItem
                      key={item.id}
                      onSelect={() => handleSelect(item)}
                      className="cursor-pointer hover:bg-blue-500 hover:text-white p-2 transition-all"
                    >
                      {item.name}
                    </CommandItem>
                  ))
                ) : (
                  <div className="p-2 text-sm text-muted-foreground">
                    Không tìm thấy.
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
              {selectedItems.map((item) => (
                <Badge
                  key={item.id}
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
                        onClick={() => updateQuantity(item.id, -1)}
                      >
                        <Minus className="w-3 h-3" />
                      </Button>
                      <Button
                        variant="ghost"
                        size="icon"
                        className="w-4 h-4 p-0 bg-transparent"
                        onClick={() => updateQuantity(item.id, 1)}
                      >
                        <Plus className="w-3 h-3" />
                      </Button>
                    </>
                  )}
                  <Button
                    variant="ghost"
                    size="icon"
                    className="w-4 h-4 p-0 ml-1 bg-transparent"
                    onClick={() => handleRemove(item.id)}
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
  );
};

export default TagSelect;
