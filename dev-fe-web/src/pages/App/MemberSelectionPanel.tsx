import { useEffect, useRef, useState } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Users, Trash2 } from "lucide-react";
import { UserService } from "@/services/admin/user.service";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { AuthService } from "@/services/auth.service";

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

const authService = new AuthService();
const userService = new UserService();

function getInitials(name: string) {
  return name
    .split(" ")
    .slice(0, 2)
    .map((word) => word[0])
    .join("")
    .toUpperCase();
}

export function MemberSelectionPanel({
  onSelectedEmailsChange,
}: MemberSelectionPanelProps) {
  const [members, setMembers] = useState<Member[]>([]);
  const [selectedMembers, setSelectedMembers] = useState<Member[]>([]);
  const [searchKeyword, setSearchKeyword] = useState("");
  const [filter, setFilter] = useState("group");

  const size = 10;
  const hasMoreRef = useRef(true);
  const pageRef = useRef(0);
  const leftListRef = useRef<HTMLDivElement>(null);

  const [self, setSelf] = useState<Member | null>(null);

  // Lấy thông tin user hiện tại (self)
  const fetchSelf = async () => {
    const profile = await authService.getProfile();
    setSelf({
      id: profile.id,
      avatarImage: profile.avatarImage,
      email: profile.email,
      name: `${profile.firstName} ${profile.lastName}`,
      code: profile.userCode,
    });
  };

  const fetchParticipants = async (
    keyword: string,
    page: number,
    size: number
  ): Promise<Member[]> => {
    const data = await userService.getListUsers(page, size, keyword);
    return data.content.map((user: any) => ({
      id: user.id,
      name: user.firstName + " " + user.lastName,
      email: user.email,
      avatarImage: user.avatarImage,
      code: `${user.userCode}`,
    }));
  };

  // Scroll xử lý phân trang
  const handleScroll = () => {
    const el = leftListRef.current;
    if (!el || !hasMoreRef.current) return;

    const scrollBottom = el.scrollHeight - el.scrollTop - el.clientHeight;
    if (scrollBottom < 10) {
      const nextPage = pageRef.current + 1;
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

  // Cập nhật danh sách member đã chọn + gọi callback
  const updateSelectedMembers = (newSelected: Member[]) => {
    setSelectedMembers(newSelected);
    onSelectedEmailsChange(newSelected.map((m) => m.email));
  };

  useEffect(() => {
    fetchSelf();
  }, []);

  // Fetch danh sách members khi mount và khi search/filter/self thay đổi
  useEffect(() => {
    if (!self) return;

    pageRef.current = 0;
    hasMoreRef.current = true;

    fetchParticipants(searchKeyword.trim(), 0, size).then((data) => {
      setMembers(data);

      // Lấy self từ data hoặc dùng thông tin self
      const selfMemberFromList = data.find((m) => m.id === self.id);
      const selfMember = selfMemberFromList ?? self;

      // Đoạn này sẽ thêm bản thân vào danh sách người tham gia
      const updateSelectedMembers = (
        newSelected: Member[] | ((prevSelected: Member[]) => Member[])
      ) => {
        setSelectedMembers((prevSelected) => {
          const updatedSelected =
            typeof newSelected === "function"
              ? newSelected(prevSelected)
              : newSelected;
          onSelectedEmailsChange(updatedSelected.map((m) => m.email));
          return updatedSelected;
        });
      };

      // đoạn này không thêm bản thân vào danh sách người tham gia
      // const updateSelectedMembers = (
      //   newSelected: Member[] | ((prevSelected: Member[]) => Member[])
      // ) => {
      //   setSelectedMembers((prevSelected) => {
      //     const updatedSelected =
      //       typeof newSelected === "function"
      //         ? newSelected(prevSelected)
      //         : newSelected;

      //     const emails = updatedSelected
      //       .filter((m) => m.id !== self?.id)
      //       .map((m) => m.email);

      //     onSelectedEmailsChange(emails);

      //     return updatedSelected;
      //   });
      // };

      updateSelectedMembers((prevSelected) => {
        const filteredWithoutSelf = prevSelected.filter(
          (m) => m.id !== selfMember.id
        );
        return [selfMember, ...filteredWithoutSelf];
      });
    });
  }, [searchKeyword, filter, self]);

  useEffect(() => {
    const el = leftListRef.current;
    el?.addEventListener("scroll", handleScroll);
    return () => el?.removeEventListener("scroll", handleScroll);
  }, []);

  const handleCheckboxChange = (member: Member, checked: boolean) => {
    if (member.id === self?.id && !checked) {
      return; // Không cho bỏ chọn self
    }
    if (checked) {
      if (!selectedMembers.find((m) => m.id === member.id)) {
        updateSelectedMembers([...selectedMembers, member]);
      }
    } else {
      updateSelectedMembers(selectedMembers.filter((m) => m.id !== member.id));
    }
  };

  const handleRemoveMember = (id: number | string) => {
    updateSelectedMembers(selectedMembers.filter((m) => m.id !== id));
  };

  return (
    <Card className="flex flex-col flex-[4.5] w-full border border-gray-400 dark:border-gray-600">
      <CardHeader className="px-4 py-3 border-b bg-muted/40">
        <CardTitle className="text-xl font-semibold text-primary flex items-center gap-2">
          <Users className="w-5 h-5 text-blue-500" />
          Thông tin thành viên
        </CardTitle>
      </CardHeader>

      <CardContent className="p-2 flex flex-col flex-1">
        <div className="flex flex-row gap-2 text-white p-0">
          <Card className="relative flex flex-col w-full border border-gray-300 dark:border-gray-600 rounded-lg overflow-hidden">
            {/* Input tìm kiếm thành viên */}
            <input
              type="text"
              placeholder="Tìm kiếm thành viên..."
              className="bg-transparent p-2 border-b border-gray-300 dark:border-gray-600 outline-none"
              value={searchKeyword}
              onChange={(e) => setSearchKeyword(e.target.value)}
            />
            <select
              className="absolute top-1 right-2 h-8 px-2 text-sm border border-gray-300 dark:border-gray-600 rounded-md bg-white dark:bg-gray-800"
              value={filter}
              onChange={(e) => setFilter(e.target.value)}
            >
              <option value="group">Nhóm</option>
              <option value="participant">Thành viên</option>
            </select>

            {/* Danh sách thành viên */}
            <div
              ref={leftListRef}
              className="flex-1 overflow-y-auto bg-white dark:bg-gray-800"
              style={{ maxHeight: "400px" }}
            >
              {members.map((member) => {
                const isChecked = selectedMembers.some(
                  (m) => m.id === member.id
                );
                return (
                  <label
                    key={member.id}
                    className="flex items-center gap-3 cursor-pointer border-b border-gray-200 dark:border-gray-700 p-2 hover:bg-gray-100 dark:hover:bg-gray-700"
                  >
                    <input
                      type="checkbox"
                      checked={isChecked}
                      onChange={(e) =>
                        handleCheckboxChange(member, e.target.checked)
                      }
                    />
                    <Avatar className="h-8 w-8">
                      <AvatarImage src={member.avatarImage} alt={member.name} />
                      <AvatarFallback>
                        {getInitials(member.name)}
                      </AvatarFallback>
                    </Avatar>
                    <div className="flex flex-col">
                      <span className="font-semibold text-sm truncate">
                        {member.name}
                      </span>
                      <span className="text-xs text-gray-500 dark:text-gray-400 truncate">
                        Email: {member.email}
                      </span>
                      <span className="text-xs text-gray-500 dark:text-gray-400 truncate">
                        Mã NV: {member.code}
                      </span>
                    </div>
                  </label>
                );
              })}
              {members.length === 0 && (
                <p className="p-3 text-center text-gray-500 dark:text-gray-400">
                  Không có thành viên phù hợp
                </p>
              )}
            </div>
          </Card>

          <Card className="max-h-[400px] flex flex-col rounded-md bg-white dark:bg-gray-900 p-3 gap-3 w-full">
            <CardTitle className="text-sm font-medium text-gray-900 dark:text-white">
              Tham gia ({selectedMembers.length})
            </CardTitle>

            <div className="flex-1 overflow-y-auto rounded-md space-y-1">
              {selectedMembers.map((member) => (
                <div
                  key={member.id}
                  className="flex items-center gap-3 p-1.5 rounded-md hover:bg-gray-100 dark:hover:bg-gray-800 border border-gray-200 dark:border-gray-700"
                >
                  <Avatar className="h-8 w-8">
                    <AvatarImage src={member.avatarImage} alt={member.name} />
                    <AvatarFallback>{getInitials(member.name)}</AvatarFallback>
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
                  {member.id !== self?.id && (
                    <Trash2
                      className="w-5 h-5 cursor-pointer text-red-500"
                      onClick={() => handleRemoveMember(member.id)}
                    />
                  )}
                </div>
              ))}
            </div>
          </Card>
        </div>
      </CardContent>
    </Card>
  );
}
