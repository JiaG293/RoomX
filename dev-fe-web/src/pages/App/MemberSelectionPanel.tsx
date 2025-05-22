import { useEffect, useRef, useState } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Users, Trash2 } from "lucide-react";
import { UserService } from "@/services/admin/user.service";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";

// interface Member như bạn đã định nghĩa
interface Member {
  id: number | string;
  name: string;
  email: string;
  code: string;
  avatarImage: string;
}

interface MemberSelectionPanelProps {
  onSelectedEmailsChange: (emails: string[]) => void;
}

export function MemberSelectionPanel({
  onSelectedEmailsChange,
}: MemberSelectionPanelProps) {
  const [members, setMembers] = useState<Member[]>([]);
  const [selectedMembers, setSelectedMembers] = useState<Member[]>([]);
  const [searchKeyword, setSearchKeyword] = useState("");
  const [filter, setFilter] = useState("group");

  // phân trang
  const size = 5;
  const hasMoreRef = useRef(true);
  const pageRef = useRef(0);

  // Hàm fetch data dựa vào từ khóa search
  const fetchParticipants = async (
    keyword: string,
    page: number,
    size: number
  ): Promise<Member[]> => {
    const userService = new UserService();
    const data = await userService.getListUsers(page, size, keyword);
    return data.content.map((user: any) => ({
      id: user.id,
      name: user.firstName + " " + user.lastName,
      email: user.email,
      avatarImage: user.avatarImage,
      code: `${user.userCode}`,
    }));
  };

  const leftListRef = useRef<HTMLDivElement>(null);
  // hàm xử lý scroll
  const handleScroll = () => {
    const el = leftListRef.current;
    if (!el || !hasMoreRef.current) return;

    const scrollBottom = el.scrollHeight - el.scrollTop - el.clientHeight;
    if (scrollBottom < 5) {
      const nextPage = pageRef.current + 1;
      console.log("Đã chạm đáy, đang fetch page", nextPage);
      fetchParticipants(searchKeyword.trim(), nextPage, size).then(
        (newData) => {
          if (newData.length === 0) {
            hasMoreRef.current = false;
            return;
          }
          setMembers((prev) => [...prev, ...newData]);
          pageRef.current = nextPage;
        }
      );
    }
  };

  // 1. useEffect chạy 1 lần khi mount để load list mặc định
  useEffect(() => {
    fetchParticipants("", 0, size).then((data) => {
      setMembers(data);
    });
  }, []);

  // 2. useEffect để fetch khi searchKeyword hoặc filter thay đổi
  useEffect(() => {
    pageRef.current = 0;
    hasMoreRef.current = true;

    fetchParticipants(searchKeyword.trim(), 0, size).then((data) => {
      setMembers(data);
      if (data.length < size) {
        hasMoreRef.current = false;
      }
    });
  }, [searchKeyword, filter]);

  // xử lý scroll
  useEffect(() => {
    const el = leftListRef.current;
    el?.addEventListener("scroll", handleScroll);
    return () => el?.removeEventListener("scroll", handleScroll);
  }, []);

  // Khi checkbox click (chọn hoặc bỏ chọn member)
  const handleCheckboxChange = (member: Member, checked: boolean) => {
    if (checked) {
      // Thêm nếu chưa có
      if (!selectedMembers.find((m) => m.id === member.id)) {
        const newSelected = [...selectedMembers, member];
        setSelectedMembers(newSelected);
        onSelectedEmailsChange(newSelected.map((m) => m.email));
      }
    } else {
      // Bỏ chọn
      const newSelected = selectedMembers.filter((m) => m.id !== member.id);
      setSelectedMembers(newSelected);
      onSelectedEmailsChange(newSelected.map((m) => m.email));
    }
  };

  // Bỏ chọn bằng nút thùng rác
  const handleRemoveMember = (id: number | string) => {
    const newSelected = selectedMembers.filter((m) => m.id !== id);
    setSelectedMembers(newSelected);
    onSelectedEmailsChange(newSelected.map((m) => m.email));
  };

  return (
    <Card className="flex flex-col flex-[4] w-full border border-gray-400 dark:border-gray-600">
      <CardHeader className="px-4 py-3 border-b bg-muted/40">
        <CardTitle className="text-xl font-semibold text-primary flex items-center gap-2">
          <Users className="w-5 h-5 text-blue-500" />
          Thông tin thành viên
        </CardTitle>
      </CardHeader>

      <CardContent className="p-2 flex flex-col flex-1">
        {/* Thanh tìm kiếm + Select box */}
        <div className="relative mb-4">
          <input
            type="text"
            placeholder="Tìm kiếm thành viên..."
            className="bg-transparent w-full h-10 pl-4 pr-32 rounded-md border border-gray-300 dark:border-gray-600 text-sm"
            value={searchKeyword}
            onChange={(e) => setSearchKeyword(e.target.value)}
          />
          <select
            className="absolute top-1/2 right-2 transform -translate-y-1/2 h-8 px-2 text-sm border border-gray-300 dark:border-gray-600 rounded-md bg-white dark:bg-gray-800"
            value={filter}
            onChange={(e) => setFilter(e.target.value)}
          >
            <option value="group">Nhóm</option>
            <option value="participant">Người tham gia</option>
          </select>
        </div>

        {/* Nội dung chia thành 2 cột ngang */}
        <div className="flex flex-row gap-2 rounded-md text-white p-0">
          {/* Cột trái: Danh sách thành viên */}
          <Card className="max-h-[400px] flex flex-col rounded-md bg-white dark:bg-gray-900 p-3 gap-3 w-full">
            <CardTitle className="text-sm font-medium text-gray-900 dark:text-white">
              Danh sách thành viên
            </CardTitle>

            {/* Danh sách phân trang */}
            <div
              ref={leftListRef}
              className="flex-1 overflow-y-auto rounded-md space-y-1"
            >
              {members.map((member) => {
                const isChecked = selectedMembers.some(
                  (m) => m.id === member.id
                );
                return (
                  <div
                    key={member.id}
                    className="flex items-center gap-3 p-1.5 rounded-md hover:bg-gray-100 dark:hover:bg-gray-800 border border-gray-200 dark:border-gray-700"
                  >
                    <input
                      type="checkbox"
                      id={`checkbox-${member.id}`}
                      checked={isChecked}
                      onChange={(e) =>
                        handleCheckboxChange(member, e.target.checked)
                      }
                    />
                    <Avatar className="h-8 w-8">
                      <AvatarImage src={member.avatarImage} alt={member.name} />
                      <AvatarFallback>
                        {member.name
                          .split(" ")
                          .map((word) => word[0])
                          .join("")
                          .toUpperCase()
                          .slice(0, 2)}
                      </AvatarFallback>
                    </Avatar>
                    <div className="flex flex-col flex-1 min-w-0">
                      <p className="font-medium text-sm truncate">
                        {member.name}
                      </p>
                      <p className="text-xs text-gray-500 truncate">
                        Email: {member.email}
                      </p>
                      <p className="text-xs text-gray-500 truncate">
                        Mã NV: {member.code}
                      </p>
                    </div>
                  </div>
                );
              })}
            </div>
          </Card>

          {/* Cột phải: Thành viên đã chọn */}
          <Card className="max-h-[400px] flex flex-col rounded-md bg-white dark:bg-gray-900 p-3 gap-3 w-full">
            <CardTitle className="text-sm font-medium text-gray-900 dark:text-white">
              Thành viên đã chọn
            </CardTitle>

            {/* Danh sách phân trang */}
            <div className="flex-1 overflow-y-auto rounded-md space-y-1">
              {selectedMembers.map((member) => (
                <div
                  key={member.id}
                  className="flex items-center gap-3 p-1.5 rounded-md hover:bg-gray-100 dark:hover:bg-gray-800 border border-gray-200 dark:border-gray-700"
                >
                  {/* Avatar */}
                  <Avatar className="h-8 w-8">
                    <AvatarImage src={member.avatarImage} alt={member.name} />
                    <AvatarFallback>
                      {member.name
                        .split(" ")
                        .slice(0, 2) // chỉ lấy 2 từ đầu tiên
                        .map((word) => word[0])
                        .join("")
                        .toUpperCase()}
                    </AvatarFallback>
                  </Avatar>

                  <div className="flex flex-col flex-1 min-w-0">
                    <p className="font-medium text-sm truncate">
                      {member.name}
                    </p>
                    <p className="text-xs text-gray-500 truncate">
                      Email: {member.email}
                    </p>
                    <p className="text-xs text-gray-500 truncate">
                      Mã NV: {member.code}
                    </p>
                  </div>

                  {/* Nút thùng rác */}
                  <Trash2
                    className="w-5 h-5 cursor-pointer text-red-500"
                    onClick={() => handleRemoveMember(member.id)}
                  />
                </div>
              ))}
            </div>
          </Card>
        </div>
      </CardContent>
    </Card>
  );
}
