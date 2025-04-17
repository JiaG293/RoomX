import React from "react";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { BranchService } from "@/services/admin/branch.service";
import { toast } from "sonner";
import CopyableInput from "@/components/admin/custom/copyable-input";
const BranchEdit: React.FC = () => {
  const { branchId } = useParams(); 
  const [branch, setBranch] = useState<any | null>(null);
  const [loading, setLoading] = useState(true);

  // State để quản lý form
  const [id, setId] = useState("");
  const [branchCode, setBranchCode] = useState("");
  const [name, setName] = useState("");
  // const [email, setEmail] = useState("");
  // const [phoneNumber, setPhoneNumber] = useState("");
  // const [address, setAddress] = useState("");

  useEffect(() => {
    if (!branchId) return;
    const fetchBranch = async () => {
      try {
        const service = new BranchService();
        const branchData = await service.getDetailBranch(branchId);
        console.log(branchData);
        // const branchData = data.content[0];
        setId(branchData.branch.id);
        setBranch(branchData.branch);
        setBranchCode(branchData.branch.code || "");
        setName(branchData.branch.name || "");
        // setEmail(branchData.email || "");
        // setPhoneNumber(branchData.phoneNumber || "");
        // setAddress(branchData.address || "");
      } catch (err) {
        toast.error("Có lỗi xảy ra");
      } finally {
        setLoading(false);
      }
    };

    fetchBranch();
  }, [branchId]);

  const handleUpdate = async () => {
    if (!branchId) return;

    const updatedData = { name, /* email, phoneNumber, address */ };

    try {
      const service = new BranchService();
      await service.updateBranch(id, updatedData);
      toast.success("Cập nhật chi nhánh thành công!");
    } catch (error) {
      toast.error("Có lỗi xảy ra khi cập nhật.");
    }
  };

  if (loading) return null;

  return (
    <div>
      {/* Thông tin chi */}
      <Card className=" max-w-xl">
        <CardHeader>
          <CardTitle>Cập Nhật Chi Nhánh</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="space-y-4">
            <div>
              <CopyableInput
                id="id"
                label="ID"
                value={id}
                // onChange={(val) => setId(val)}
              />
            </div>

            <div>
              <CopyableInput
                id="branchCode"
                label="Mã chi nhánh"
                value={branchCode}
                // onChange={(e) => setBranchCode(e.target.value)}
              />
            </div>

            <div>
              <Label htmlFor="name">Tên Chi Nhánh</Label>
              <Input
                id="name"
                value={name}
                onChange={(e) => setName(e.target.value)}
              />
            </div>

            {/* <div>
              <Label htmlFor="email">Email</Label>
              <Input
                id="email"
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </div>

            <div>
              <Label htmlFor="phoneNumber">Số Điện Thoại</Label>
              <Input
                id="phoneNumber"
                value={phoneNumber}
                onChange={(e) => setPhoneNumber(e.target.value)}
              />
            </div>

            <div>
              <Label htmlFor="address">Địa Chỉ</Label>
              <Input
                id="address"
                value={address}
                onChange={(e) => setAddress(e.target.value)}
              />
            </div> */}

            <Button className="w-full" onClick={handleUpdate}>
              Cập Nhật
            </Button>
          </div>
        </CardContent>
      </Card>
    </div>
  );
};

export default BranchEdit;
