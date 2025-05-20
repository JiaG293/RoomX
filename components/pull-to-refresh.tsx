import { useState } from "react";
import { FlatList, RefreshControl, ListRenderItem } from "react-native";

export default function PullToRefresh({
  children,
  data,
  renderItem,
  keyExtractor,
}: {
  children?: React.ReactNode;
  data: any[];
  renderItem: ListRenderItem<any>; // Đảm bảo kiểu dữ liệu đúng
  keyExtractor: (item: any) => string;
}) {
  const [refreshing, setRefreshing] = useState(false);

  const onRefresh = () => {
    setRefreshing(true);
    setTimeout(() => {
      setRefreshing(false);
    }, 500); // Giả lập thời gian reload
  };

  return (
    <FlatList
      data={data}
      renderItem={renderItem} // Truyền đúng kiểu ListRenderItem
      keyExtractor={keyExtractor}
      refreshControl={<RefreshControl refreshing={refreshing} onRefresh={onRefresh} />}
    />
  );
}
