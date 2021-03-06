package zio.keeper

import zio.stream.ZStream
import zio.{ Chunk, Has, ZIO }

package object membership {
  type Membership = Has[Membership.Service]

  def broadcast(data: Chunk[Byte]): ZIO[Membership, Error, Unit] =
    ZIO.accessM[Membership](_.get.broadcast(data))

  def events: ZStream[Membership, Error, MembershipEvent] =
    ZStream.unwrap(ZIO.access[Membership](_.get.events))

  def localMember: ZIO[Membership, Nothing, Member] =
    ZIO.accessM[Membership](_.get.localMember)

  def nodes: ZIO[Membership, Nothing, List[NodeId]] =
    ZIO.accessM[Membership](_.get.nodes)

  def receive: ZStream[Membership, Error, Message] =
    ZStream.unwrap(ZIO.access[Membership](_.get.receive))

  def send(data: Chunk[Byte], receipt: NodeId): ZIO[Membership, Error, Unit] =
    ZIO.accessM[Membership](_.get.send(data, receipt))
}
